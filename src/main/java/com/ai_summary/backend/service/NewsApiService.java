package com.ai_summary.backend.service;

import com.ai_summary.backend.dto.NewsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsApiService {

    private final String API_KEY = "pub_0bf3416a4e764138bd341c835af1f2eb";
    public NewsResponse fetchNews(){

        String url = "https://newsdata.io/api/1/news?apikey="
                + API_KEY
                + "&language=en&category=technology";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, NewsResponse.class);
    }
}
