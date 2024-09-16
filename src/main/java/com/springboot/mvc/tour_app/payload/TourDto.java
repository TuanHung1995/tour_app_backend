package com.springboot.mvc.tour_app.payload;

import lombok.Data;

@Data
public class TourDto {

    private Long id;
    private String name;
    private String description;
    private String location;
    private String price;
    private String duration;
    private String start_date;
    private String end_date;
    private String image;
    private String status;

}
