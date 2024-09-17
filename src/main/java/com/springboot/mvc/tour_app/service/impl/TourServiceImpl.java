package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.service.TourService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    private final ModelMapper mapper;
    private final TourRepository tourRepository;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository) {
        this.mapper = mapper;
        this.tourRepository = tourRepository;
    }

    // Get all tours
    @Override
    public List<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream()
                .map(this::convertTourToTourDto)
                .collect(Collectors.toList());
    }

    // Get tour by id
    @Override
    public TourDto getTourById(long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
        return convertTourToTourDto(tour);
    }

    // Create tour
    @Override
    public TourDto createTour(TourDto tourDto) {
        Tour tour = convertTourDtoToTour(tourDto);
        Tour newTour = tourRepository.save(tour);
        return convertTourToTourDto(newTour);
    }

    // Update tour
    @Override
    public TourDto updateTour(long id, TourDto tourDto) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
        tour.setName(tourDto.getName());
        tour.setDescription(tourDto.getDescription());
        tour.setPrice(tourDto.getPrice());
        tour.setLocation(tourDto.getLocation());
        tour.setDuration(tourDto.getDuration());
        tour.setEndDate(tourDto.getEndDate());
        tour.setStartDate(tourDto.getStartDate());
        tour.setImage(tourDto.getImage());
        tour.setStatus(tourDto.getStatus());
        Tour updatedTour = tourRepository.save(tour);
        return convertTourToTourDto(updatedTour);
    }

    // Delete tour
    @Override
    public String deleteTour(long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
        tourRepository.delete(tour);
        return "Tour deleted successfully!";
    }

    // Pagination tour by status
    @Override
    public TourResponse getAllToursByStatus(String status, int page, int size) {

        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Get tours by status
        Page<Tour> tours = tourRepository.findAllToursByStatus(status, pageable);

        // Convert tours to TourDto
        List<Tour> content = tours.getContent();
        List<TourDto> tourDtoList = content.stream()
                .map(this::convertTourToTourDto)
                .toList();

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
