package com.ai_summary.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class Aiservice {

    @Value("${groq.api.key}")
    private String apiKey;

    public String askGemini(String prompt) {
        try {
            String url = "https://api.groq.com/openai/v1/chat/completions";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> body = Map.of(
                    "model", "llama-3.1-8b-instant",
                    "messages", new Object[]{
                            Map.of("role", "user", "content", prompt)
                    },
                    "temperature", 0.5,
                    "max_tokens", 300
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return root.get("choices").get(0)
                    .get("message").get("content").asText();

        } catch (Exception e) {
            System.out.println("AI call failed: " + e.getMessage());
            return "AI processing unavailable";
        }
    }
}