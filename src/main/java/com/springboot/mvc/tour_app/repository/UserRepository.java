package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
