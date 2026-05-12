package com.ai_summary.backend.service;

import com.ai_summary.backend.dto.NewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@Service
public class NewsApiService {

    @Value("${newsdata.api.key}")
    private String apiKey;

    private static final int MAX_PAGES = 5;

    public NewsResponse fetchNews() {
        RestTemplate restTemplate = new RestTemplate();
        NewsResponse combined = new NewsResponse();
        combined.setResults(new ArrayList<>());
        String nextPage = null;
        int pagesFetched = 0;

        while (pagesFetched < MAX_PAGES) {
            try {
                String url = "https://newsdata.io/api/1/news?apikey=" + apiKey
                        + "&language=en&category=technology";
                if (nextPage != null) url += "&page=" + nextPage;

                NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
                if (response == null || response.getResults() == null) break;

                combined.getResults().addAll(response.getResults());
                nextPage = response.getNextPage();
                pagesFetched++;
                if (nextPage == null) break;
            } catch (Exception e) {
                System.out.println("News API error on page " + pagesFetched + ": " + e.getMessage());
                break;
            }
        }
        return combined;
    }
}
