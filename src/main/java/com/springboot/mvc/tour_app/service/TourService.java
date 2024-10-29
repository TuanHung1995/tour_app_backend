package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;

import java.util.List;

public interface TourService {

    List<TourDto> getAllTours();

    TourDto getTourById(long id);

    TourDto createTour(TourDto tourDto);

    TourDto updateTour(long id, TourDto tourDto);

    String deleteTour(long id);

    TourResponse getAllToursByStatus(String status, int page, int size);

    TourResponse sortingTours(String status, String location, String destination, Long minPrice, Long maxPrice, String direction, String sortBy, int page, int size);

    List<TourDto> getAllToursByCategoryAndStatus(String category, String status);

}
