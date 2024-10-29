package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.Category;
import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.TourResponse;
import com.springboot.mvc.tour_app.repository.CategoryRepository;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.service.TourService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    private final ModelMapper mapper;
    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.tourRepository = tourRepository;
        this.categoryRepository = categoryRepository;
    }

    // Get all tours
    @Override
    public List<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        List<String> categories = tours.stream()
                .map(tour -> tour.getCategory().getName())
                .toList();
        List<TourDto> toursDto = tours.stream()
                .map(this::convertTourToTourDto)
                .toList();
        // Set category for each tour
        for (int i = 0; i < toursDto.size(); i++) {
            toursDto.get(i).setCategory(categories.get(i));
        }

        return toursDto;
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
        Category category = categoryRepository.findByName(tourDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", tourDto.getId()));
        tour.setCategory(category);
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

    @Override
    public TourResponse sortingTours(String status, String location, String destination, Long maxPrice, Long minPrice, String direction, String sortBy, int page, int size) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get tours by status, location, price
        Page<Tour> tours = tourRepository.findByStatusAndLocationContainingAndDestinationContainingAndPriceBetween(status, location, destination, minPrice, maxPrice, pageable);

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

    // Get all tours by category and status
    @Override
    public List<TourDto> getAllToursByCategoryAndStatus(String category, String status) {
        Category categoryEntity = categoryRepository.findByName(category)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", 404));
        List<Tour> tours = tourRepository.findAllByCategoryAndStatus(categoryEntity, status);
        return tours.stream()
                .map(this::convertTourToTourDto)
                .collect(Collectors.toList());
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
