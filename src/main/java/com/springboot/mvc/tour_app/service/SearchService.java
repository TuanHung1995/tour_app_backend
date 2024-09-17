package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface SearchService {

    TourResponse searchTours(String keyword,
                             String status,
                             String category,
                             String destination,
                             String location,
                             Date start_date,
                             Date end_date,
                             int page, int size);

}
