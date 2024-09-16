package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
