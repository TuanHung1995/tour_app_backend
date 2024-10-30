package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCartId(Long cartId);

}
