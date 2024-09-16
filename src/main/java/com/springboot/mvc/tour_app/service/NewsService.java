package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.payload.NewsDto;

import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();

    NewsDto getNewsById(long id);

    NewsDto createNews(NewsDto newsDto);

    NewsDto updateNews(long id, NewsDto newsDto);

    String deleteNews(long id);

}
