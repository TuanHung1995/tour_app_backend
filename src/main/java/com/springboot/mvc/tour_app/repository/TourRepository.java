package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Category;
import com.springboot.mvc.tour_app.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<Tour> findAllToursByStatus(String status, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatus(Category categoryId, String status, Pageable pageable);

    Page<Tour> findAllToursByCategoryIdAndStatusAndDestination(Long categoryId, String status, String destination, Pageable pageable);

    Page<Tour> findAllToursByCategoryIdAndStatusAndDestinationAndLocation(Long categoryId, String status, String destination, String location, Pageable pageable);

    Page<Tour> findAllToursByCategoryIdAndStatusAndDestinationAndLocationAndStartDate(Long categoryId, String status, String destination, String location, Date start_date, Pageable pageable);

    Page<Tour> findAllToursByCategoryIdAndStatusAndDestinationAndLocationAndStartDateAndEndDate(Long categoryId, String status, String destination, String location, Date start_date, Date end_date, Pageable pageable);

}
