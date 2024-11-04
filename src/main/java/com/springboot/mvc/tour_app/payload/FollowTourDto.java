package com.springboot.mvc.tour_app.payload;

import lombok.Data;

@Data
public class FollowTourDto {

        private Long id;
        private UserDto user;
        private long tourId;
        private TourDto tour;

}
