package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.payload.FollowTourDto;
import com.springboot.mvc.tour_app.payload.FollowTourResponse;
import com.springboot.mvc.tour_app.payload.TourDto;

public interface FollowService {

    FollowTourResponse followTour(Long userId, FollowTourDto followTourDto);
    FollowTourResponse unfollowTour(Long userId, Long followTourId);
    FollowTourResponse getFollowedTours(Long userId);

}
