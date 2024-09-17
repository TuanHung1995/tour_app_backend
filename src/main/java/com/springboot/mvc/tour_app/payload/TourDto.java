package com.springboot.mvc.tour_app.payload;

import com.springboot.mvc.tour_app.entity.Category;
import lombok.Data;

import java.util.Date;

@Data
public class TourDto {

    private Long id;
    private String name;
    private String description;
    private String location;
    private String destination;
    private long price;
    private int duration;
    private Date startDate;
    private Date endDate;
    private String image;
    private String status;
    private Category category;

}
