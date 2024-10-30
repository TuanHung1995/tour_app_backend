package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.payload.CartResponse;
import com.springboot.mvc.tour_app.payload.ItemDto;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // Get Cart by User Id
    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart(getCurrentUser().getId());
    }

    // Add Item to Cart
    @PostMapping
    public CartResponse addItemToCart(@RequestBody ItemDto itemDto){
        return cartService.addItemToCart(itemDto, getCurrentUser().getId());
    }

    // Remove Item from Cart
    @DeleteMapping("/item/{itemId}")
    public CartResponse removeItemFromCart(@PathVariable("itemId") long itemId){
        return cartService.removeItemFromCart(itemId, getCurrentUser().getId());
    }

    // Clear Cart
    @DeleteMapping("/{cartId}/clear")
    public CartResponse clearCart(@PathVariable("cartId") Long cartId){
        return cartService.clearCart(cartId);
    }

    // Modify Item Quantity
    @PutMapping()
    public CartResponse modifyItemQuantity(@RequestBody ItemDto itemDto){
        return cartService.modifyItemQuantity(itemDto, getCurrentUser().getId());
    }

    // Lấy thông tin người dùng hiện tại
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUsername = userDetails.getUsername();
            // Lấy thông tin người dùng từ database
            return userRepository.findByEmail(currentUsername);
        }

        // Return null if no user is logged in
        return null;
    }


}
