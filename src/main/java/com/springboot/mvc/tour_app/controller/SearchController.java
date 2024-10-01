package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.service.SearchService;
import com.springboot.mvc.tour_app.utils.AppConstraint;
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
                                    @RequestParam(value = "status", defaultValue = "available", required = false) String status,
                                    @RequestParam(value = "category", defaultValue = "", required = false) String category,
                                    @RequestParam(value = "destination", defaultValue = "", required = false) String destination,
                                    @RequestParam(value = "location", defaultValue = "", required = false) String location,
                                    @RequestParam(value = "start_date", defaultValue = "", required = false) Date start_date,
                                    @RequestParam(value = "end_date", defaultValue = "", required = false) Date end_date,
                                    @RequestParam(value = "page", defaultValue = AppConstraint.PAGE_DEFAULT_NUMBER, required = false) int page,
                                    @RequestParam(value = "size", defaultValue = AppConstraint.PAGE_DEFAULT_SIZE, required = false) int size) {
        return searchService.searchTours(keyword, status, category, destination, location, start_date, end_date, page, size);
    }

}
