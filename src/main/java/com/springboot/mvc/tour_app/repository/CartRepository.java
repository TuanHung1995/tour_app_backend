package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
