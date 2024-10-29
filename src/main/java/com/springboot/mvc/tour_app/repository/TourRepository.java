package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Category;
import com.springboot.mvc.tour_app.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<Tour> findAllToursByStatus(String status, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatus(Category categoryId, String status, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatusAndDestination(Category categoryId, String status, String destination, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatusAndDestinationAndLocation(Category categoryId, String status, String destination, String location, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatusAndDestinationAndLocationAndStartDate(Category categoryId, String status, String destination, String location, Date start_date, Pageable pageable);

    Page<Tour> findAllToursByCategoryAndStatusAndDestinationAndLocationAndStartDateAndEndDate(Category categoryId, String status, String destination, String location, Date start_date, Date end_date, Pageable pageable);

    List<Tour> findAllByCategoryAndStatus(Category categoryId, String status);

    // Sorting Page
    Page<Tour> findByStatusAndLocationContainingAndDestinationContainingAndPriceBetween(
            String status,
            String location,
            String destination,
            long minPrice,
            long maxPrice,
            Pageable pageable);
}
