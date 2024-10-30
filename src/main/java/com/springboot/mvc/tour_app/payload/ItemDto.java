package com.springboot.mvc.tour_app.payload;

import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private int quantity;
    private long price;
    private long tourId;
    private TourDto tour;
}
