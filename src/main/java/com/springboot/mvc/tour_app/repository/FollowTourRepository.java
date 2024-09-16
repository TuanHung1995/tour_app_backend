package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.FollowTour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowTourRepository extends JpaRepository<FollowTour, Long> {
}
