package com.ai_summary.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponse {

    private List<NewsArticleDto> results;
    private String nextPage;
}
