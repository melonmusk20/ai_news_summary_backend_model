package com.ai_summary.backend.dto;

import lombok.Data;

@Data
public class NewsArticleDto {

    private String title;
    private String description;
    private String source_id;
    private String link;
}