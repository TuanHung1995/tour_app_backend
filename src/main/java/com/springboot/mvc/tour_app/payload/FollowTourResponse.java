package com.springboot.mvc.tour_app.payload;

import lombok.Data;

import java.util.List;

@Data
public class FollowTourResponse {

    private Long id;
    private UserDto user;
    List<TourDto> tours;

}
