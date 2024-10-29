package com.springboot.mvc.tour_app.payload;

import com.springboot.mvc.tour_app.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class NewsDto {

    private Long id;
    private String title;
    private String content;
    private Date created_at;
    private String status;
    private String image;
    private String author;

}
