package com.ai_summary.backend.service;

import com.ai_summary.backend.dto.NewsArticleDto;
import com.ai_summary.backend.dto.NewsResponse;
import com.ai_summary.backend.model.Article;
import com.ai_summary.backend.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import com.ai_summary.backend.util.TextCleaner;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final NewsApiService newsApiService;
    private final ArticleRepository articleRepository;
    private final Aiservice aiService;

    public ArticleService(
            NewsApiService newsApiService,
            ArticleRepository articleRepository,
            Aiservice aiService
    ) {
        this.newsApiService = newsApiService;
        this.articleRepository = articleRepository;
        this.aiService = aiService;
    }

    // Fetch from API + AI processing + Save in DB
    public List<Article> fetchAndSaveArticles() {

        NewsResponse response = newsApiService.fetchNews();

        List<Article> savedArticles = new ArrayList<>();

        if (response == null || response.getResults() == null) {
            return savedArticles;
        }

        for (NewsArticleDto dto : response.getResults()) {
            try {
                if (!articleRepository.existsByArticleUrl(dto.getLink())) {
                    Article article = new Article();
                    article.setTitle(dto.getTitle());

                   String content = TextCleaner.clean(dto.getDescription());
                    article.setContent(content);

                    article.setSummary(
                            aiService.askGemini("Summarize in 2 sentences:\n" + content)
                    );
                    article.setSentiment(
                            aiService.askGemini("Sentiment as one word (Positive/Negative/Neutral):\n" + content)
                    );
                    article.setInsights(
                            aiService.askGemini("3 key insights as bullets:\n" + content)
                    );

                    article.setSource(dto.getSource_id());
                    article.setArticleUrl(dto.getLink());

                    savedArticles.add(articleRepository.save(article));
                }
            } catch (Exception e) {
                System.out.println("Skipping article: " + e.getMessage());
            }
        }

        return savedArticles;
    }
    // Get all articles
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
