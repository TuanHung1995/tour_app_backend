package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.service.TourService;
import com.springboot.mvc.tour_app.utils.AppConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    // Get all tours
    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours() {
        return ResponseEntity.ok(tourService.getAllTours());
    }

    // Get tour by id
    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable("id") long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    // Create tour
    @PostMapping
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        return new ResponseEntity<>(tourService.createTour(tourDto), HttpStatus.CREATED);
    }

    // Update tour
    @PutMapping("/{id}")
    public ResponseEntity<TourDto> updateTour(@PathVariable("id") long id, @RequestBody TourDto tourDto) {
        return new ResponseEntity<>(tourService.updateTour(id, tourDto), HttpStatus.OK);
    }

    // Delete tour
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable("id") long id) {
        return new ResponseEntity<>(tourService.deleteTour(id), HttpStatus.OK);
    }

    // Pagination tours by status
    @GetMapping("/page")
    public TourResponse getAllToursByStatus(@RequestParam(defaultValue = "") String status,
                                            @RequestParam(value = AppConstraint.PAGE_DEFAULT_NUMBER, required = false) int page,
                                            @RequestParam(value = AppConstraint.PAGE_DEFAULT_SIZE, required = false) int size) {
        return tourService.getAllToursByStatus(status, page, size);
    }

    // Get all tours by category and status
    @GetMapping("/category")
    public List<TourDto> getAllToursByCategoryAndStatus(@RequestParam(defaultValue = "") String category,
                                                        @RequestParam(defaultValue = "") String status) {
        return tourService.getAllToursByCategoryAndStatus(category, status);
    }

}
