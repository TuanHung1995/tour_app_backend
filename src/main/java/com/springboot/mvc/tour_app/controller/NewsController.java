package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.payload.NewsDto;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;
    private final UserRepository userRepository;

    public NewsController(NewsService newsService, UserRepository userRepository) {
        this.newsService = newsService;
        this.userRepository = userRepository;
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
        return new ResponseEntity<>(newsService.createNews(newsDto, getCurrentUser().getId()), HttpStatus.CREATED);
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

    // Lấy thông tin người dùng hiện tại
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUsername = userDetails.getUsername();
            // Lấy thông tin người dùng từ database
            return userRepository.findByEmail(currentUsername);
        }

        // Return null if no user is logged in
        return null;
    }

}
