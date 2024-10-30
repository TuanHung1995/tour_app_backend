package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.Cart;
import com.springboot.mvc.tour_app.entity.Item;
import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.CartResponse;
import com.springboot.mvc.tour_app.payload.ItemDto;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.repository.CartRepository;
import com.springboot.mvc.tour_app.repository.ItemRepository;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository, TourRepository tourRepository, UserRepository userRepository, ModelMapper mapper) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public CartResponse addItemToCart(ItemDto itemDto, Long userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Cart newestCart = cart.orElse(null);
        if (cart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newestCart = cartRepository.save(newCart);
        }

        itemDto.setTour(convertTourToTourDto(tourRepository.findById(itemDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", itemDto.getTourId()))));
        itemDto.setPrice(itemDto.getQuantity() * itemDto.getTour().getPrice());


        Item item = convertItemDtoToItem(itemDto);
        item.setCart(newestCart);
        itemRepository.save(item);

        return updateCartResponse(newestCart, newestCart.getId());
    }

    @Override
    public CartResponse removeItemFromCart(long itemId, Long cartId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        Cart cart = cartRepository.findByUserId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
        itemRepository.delete(item);
        return updateCartResponse(cart, cartId);
    }

    @Override
    public CartResponse clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
        List<Item> items = itemRepository.findByCartId(cartId);
        if (!items.isEmpty()) {
            itemRepository.deleteAll(items);
        }
        return convertCartToCartResponse(cart);
    }

    @Override
    public CartResponse modifyItemQuantity(ItemDto itemDto, Long userId) {
        itemDto.setTour(convertTourToTourDto(tourRepository.findById(itemDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", itemDto.getTourId()))));
        itemDto.setPrice(itemDto.getQuantity() * itemDto.getTour().getPrice());
        Item item = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemDto.getId()));
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());
        itemRepository.save(item);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", userId));
        return updateCartResponse(cart, userId);
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));
        return updateCartResponse(cart, cart.getId());
    }

    // Update cart response
    private CartResponse updateCartResponse(Cart cart, Long cartId) {
        CartResponse cartResponse = convertCartToCartResponse(cart);
        List<Item> items = itemRepository.findByCartId(cartId);
        if (!items.isEmpty()) {
            double total = items.stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .sum();
            cartResponse.setTotal(total);
            int totalQuantity = items.stream()
                    .mapToInt(Item::getQuantity)
                    .sum();
            cartResponse.setQuantity(totalQuantity);
            cartResponse.setItems(items.stream()
                    .map(this::convertItemToItemDto)
                    .toList());
            cartRepository.save(cart);
        }
        return cartResponse;
    }

    // Convert Tour to TourDto
    private TourDto convertTourToTourDto(Tour post) {
        return mapper.map(post, TourDto.class);
    }

    // Convert TourDto to Tour
    private Tour convertTourDtoToTour(TourDto postDto) {
        return mapper.map(postDto, Tour.class);
    }

    // Convert Item to ItemDto
    private ItemDto convertItemToItemDto(Item item) {
        return mapper.map(item, ItemDto.class);
    }

    // Convert ItemDto to Item
    private Item convertItemDtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setTour(convertTourDtoToTour(itemDto.getTour()));
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getTour().getPrice());
        return item;
//        return mapper.map(itemDto, Item.class);
    }

    // Convert Cart to CartResponse
    private CartResponse convertCartToCartResponse(Cart cart) {
        return mapper.map(cart, CartResponse.class);
    }

    // Convert CartResponse to Cart
    private Cart convertTourDtoToTour(CartResponse cartResponse) {
        return mapper.map(cartResponse, Cart.class);
    }
}
