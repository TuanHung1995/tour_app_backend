package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<Tour> findAllToursByStatus(String status, Pageable pageable);

}
