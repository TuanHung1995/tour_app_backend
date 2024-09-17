package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final TourRepository tourRepository;
    private final ModelMapper mapper;

    public SearchServiceImpl(TourRepository tourRepository, ModelMapper mapper) {
        this.tourRepository = tourRepository;
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

        if (!destination.isEmpty() && !location.isEmpty() && start_date != null && end_date != null) {
            tours = tourRepository.findAllToursByCategoryIdAndStatusAndDestinationAndLocationAndStartDateAndEndDate(Long.parseLong(category), status, destination, location, start_date, end_date, pageable);
        } else if (!destination.isEmpty() && !location.isEmpty() && start_date != null) {
            tours = tourRepository.findAllToursByCategoryIdAndStatusAndDestinationAndLocationAndStartDate(Long.parseLong(category), status, destination, location, start_date, pageable);
        } else if (!destination.isEmpty() && !location.isEmpty()) {
            tours = tourRepository.findAllToursByCategoryIdAndStatusAndDestinationAndLocation(Long.parseLong(category), status, destination, location, pageable);
        } else if (!destination.isEmpty()) {
            tours = tourRepository.findAllToursByCategoryIdAndStatusAndDestination(Long.parseLong(category), status, destination, pageable);
        } else {
            tours = tourRepository.findAllToursByStatus(status, pageable);
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
