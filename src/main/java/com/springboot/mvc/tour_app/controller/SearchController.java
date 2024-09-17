package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // Search tours
    @GetMapping
    public TourResponse searchTours(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                    @RequestParam(value = "status", defaultValue = "available") String status,
                                    String category,
                                    String destination,
                                    String location,
                                    Date start_date,
                                    Date end_date,
                                    int page, int size) {
        return searchService.searchTours(keyword, status, category, destination, location, start_date, end_date, page, size);
    }

}
