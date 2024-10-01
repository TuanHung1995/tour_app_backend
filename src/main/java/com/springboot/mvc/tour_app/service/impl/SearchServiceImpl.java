package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.Category;
import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.repository.CategoryRepository;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public SearchServiceImpl(TourRepository tourRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.tourRepository = tourRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public TourResponse searchTours(String keyword,
                                     String status,
                                     String category,
                                     String destination,
                                     String location,
                                     Date start_date,
                                     Date end_date,
                                     int page, int size) {
        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Get tours by status
        Page<Tour> tours = null;

        // Find category by name
        Category categorySearch = categoryRepository.findByName(category).orElseThrow(() -> new ResourceNotFoundException("Category", "name", 0));

        if (!destination.isEmpty() && !location.isEmpty() && start_date != null && end_date != null) {
            tours = tourRepository.findAllToursByCategoryAndStatusAndDestinationAndLocationAndStartDateAndEndDate(categorySearch, status, destination, location, start_date, end_date, pageable);
        } else if (!destination.isEmpty() && !location.isEmpty() && start_date != null) {
            tours = tourRepository.findAllToursByCategoryAndStatusAndDestinationAndLocationAndStartDate(categorySearch, status, destination, location, start_date, pageable);
        } else if (!destination.isEmpty() && !location.isEmpty()) {
            tours = tourRepository.findAllToursByCategoryAndStatusAndDestinationAndLocation(categorySearch, status, destination, location, pageable);
        } else if (!destination.isEmpty()) {
            tours = tourRepository.findAllToursByCategoryAndStatusAndDestination(categorySearch, status, destination, pageable);
        } else {
            tours = tourRepository.findAllToursByCategoryAndStatus(categorySearch,status, pageable);
        }

        // Convert tours to TourDto
        List<Tour> content = tours.getContent();
        List<TourDto> tourDtoList = new java.util.ArrayList<>(content.stream()
                .map(this::convertTourToTourDto)
                .toList());

        for (TourDto tourDto : tourDtoList) {
            if (!keyword.isEmpty()) {
                if (!tourDto.getName().contains(keyword) && !tourDto.getDescription().contains(keyword)) {
                    tourDtoList.remove(tourDto);
                }
            }
        }

        // Create TourResponse object
        TourResponse response = new TourResponse();
        response.setContent(tourDtoList);
        response.setPage(tours.getNumber());
        response.setSize(tours.getSize());
        response.setTotalElements(tours.getTotalElements());
        response.setTotalPages(tours.getTotalPages());
        response.setLast(tours.isLast());

        return response;
    }

    // Convert Tour to TourDto
    private TourDto convertTourToTourDto(Tour post) {
        return mapper.map(post, TourDto.class);
    }

    // Convert TourDto to Tour
    private Tour convertTourDtoToTour(TourDto postDto) {
        return mapper.map(postDto, Tour.class);
    }

}
