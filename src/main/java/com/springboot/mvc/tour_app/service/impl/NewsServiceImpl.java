package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.News;
import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.NewsDto;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.repository.NewsRepository;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, ModelMapper mapper) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    // Get all news
    @Override
    public List<NewsDto> getAllNews() {
        List<News> news = newsRepository.findAll();
        List<User> users = new ArrayList<>();
        for (News n : news) {
            users.add(n.getUser());
        }
        List<NewsDto> result = news.stream()
                .map(this::convertNewsToNewsDto)
                .toList();

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setAuthor(users.get(i).getFull_name());
        }

        return result;
    }

    // Get news by id
    @Override
    public NewsDto getNewsById(long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", id));
        return convertNewsToNewsDto(news);
    }

    // Create news
    @Override
    public NewsDto createNews(NewsDto newsDto, long userId) {
        News news = convertNewsDtoToNews(newsDto);
        news.setCreated_at(new Date());
        news.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)));
        News newNews = newsRepository.save(news);
        NewsDto result = convertNewsToNewsDto(newNews);
        result.setAuthor(newNews.getUser().getFull_name());
        return result;
    }

    // Update news
    @Override
    public NewsDto updateNews(long id, NewsDto newsDto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", id));
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setStatus(newsDto.getStatus());
        News updatedNews = newsRepository.save(news);
        return convertNewsToNewsDto(updatedNews);
    }

    // Delete news
    @Override
    public String deleteNews(long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", id));
        newsRepository.delete(news);
        return "News deleted successfully!";
    }

    // Convert News to NewsDto
    private NewsDto convertNewsToNewsDto(News news) {
        return mapper.map(news, NewsDto.class);
    }

    // Convert NewsDto to News
    private News convertNewsDtoToNews(NewsDto newsDto) {
        return mapper.map(newsDto, News.class);
    }
}
