package com.springboot.mvc.tour_app.payload;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {

    private Long id;
    private int quantity;
    private double total;
    private List<ItemDto> items;

}
