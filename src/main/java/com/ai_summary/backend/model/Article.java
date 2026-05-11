package com.ai_summary.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;


    @Column(columnDefinition = "TEXT")
    private String summary;

    private String sentiment;

    @Column(columnDefinition = "TEXT")
    private String insights;

    private String source;

    @Column(length = 1000)
    private String articleUrl;

}