package com.ai_summary.backend.controller;

import com.ai_summary.backend.model.Article;
import com.ai_summary.backend.repository.ArticleRepository;
import com.ai_summary.backend.service.ArticleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class NewsController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public NewsController(
            ArticleService articleService,
            ArticleRepository articleRepository
    ) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Backend Running";
    }

    @GetMapping("/fetch-news")
    public List<Article> fetchNews() {
        return articleService.fetchAndSaveArticles();
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}