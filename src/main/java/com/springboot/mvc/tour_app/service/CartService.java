package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.payload.CartResponse;
import com.springboot.mvc.tour_app.payload.ItemDto;

public interface CartService {

    CartResponse addItemToCart(ItemDto itemDto, Long userId);
    CartResponse removeItemFromCart(long itemId, Long userId);
    CartResponse clearCart(Long userId);
    CartResponse modifyItemQuantity(ItemDto itemDto, Long userId);
    CartResponse getCart(Long userId);
}
