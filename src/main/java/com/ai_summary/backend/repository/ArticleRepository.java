package com.ai_summary.backend.repository;

import com.ai_summary.backend.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>{

    boolean existsByArticleUrl(String articleUrl);
}
