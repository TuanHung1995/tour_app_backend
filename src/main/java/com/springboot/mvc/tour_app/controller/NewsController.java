package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.payload.NewsDto;
import com.springboot.mvc.tour_app.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Get all news
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // Get news by id
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable("id") long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    // Create news
    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto newsDto) {
        return new ResponseEntity<>(newsService.createNews(newsDto), HttpStatus.CREATED);
    }

    // Update news
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable("id") long id, @RequestBody NewsDto newsDto) {
        return new ResponseEntity<>(newsService.updateNews(id, newsDto), HttpStatus.OK);
    }

    // Delete news
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") long id) {
        return new ResponseEntity<>(newsService.deleteNews(id), HttpStatus.OK);
    }

}
