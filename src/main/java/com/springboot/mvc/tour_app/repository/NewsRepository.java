package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
