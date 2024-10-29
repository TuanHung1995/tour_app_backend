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
@CrossOrigin(origins = "http://localhost:4200")
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

    // Sorting tours by status, location, price
    @GetMapping("/sort")
    public TourResponse sortingTours(@RequestParam(defaultValue = "") String status,
                                     @RequestParam(defaultValue = "", required = false) String location,
                                     @RequestParam(defaultValue = "", required = false) String destination,
                                     @RequestParam(defaultValue = "0", required = false) Long minPrice,
                                     @RequestParam(defaultValue = "" , required = false) Long maxPrice,
                                     @RequestParam(defaultValue = "asc", required = false) String direction,
                                     @RequestParam(defaultValue = "id", required = false) String sortBy,
                                     @RequestParam(defaultValue = AppConstraint.PAGE_DEFAULT_NUMBER, required = false) int page,
                                     @RequestParam(defaultValue = AppConstraint.PAGE_DEFAULT_SIZE, required = false) int size) {
        return tourService.sortingTours(status, location, destination, minPrice, maxPrice, direction, sortBy, page, size);
    }

    // Get all tours by category and status
    @GetMapping("/category")
    public List<TourDto> getAllToursByCategoryAndStatus(@RequestParam(defaultValue = "") String category,
                                                        @RequestParam(defaultValue = "") String status) {
        return tourService.getAllToursByCategoryAndStatus(category, status);
    }

}
