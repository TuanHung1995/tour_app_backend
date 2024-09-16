package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
