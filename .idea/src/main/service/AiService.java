package ru.nurislam.springcourse.movieprojectmain.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // CACHE
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    // RATE LIMIT
    private final Map<String, Long> lastRequest = new ConcurrentHashMap<>();

    public AiService(WebClient.Builder builder,
                     @Value("${openai.api.key}") String apiKey) {

        this.webClient = builder
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String ask(String user, String message) {

        if (!isAllowed(user)) {
            return "⏳ Too many requests. Wait a bit.";
        }

        if (cache.containsKey(message)) {
            return cache.get(message);
        }

        String answer = retryRequest(message);

        cache.put(message, answer);

        return answer;
    }

    private String retryRequest(String message) {

        int attempts = 3;

        for (int i = 0; i < attempts; i++) {
            try {
                return sendRequest(message);

            } catch (WebClientResponseException.TooManyRequests e) {
                sleep(i);

            } catch (WebClientResponseException.Unauthorized e) {
                return "❌ Invalid API key (401)";
            }
        }

        return "AI is busy. Try again later.";
    }

    private String sendRequest(String message) {

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", message)
                )
        );

        String response = webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractText(response);
    }

    private String extractText(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            return "⚠️ Failed to parse AI response";
        }
    }

    private boolean isAllowed(String user) {

        long now = System.currentTimeMillis();

        if (lastRequest.containsKey(user)) {
            long last = lastRequest.get(user);
            if (now - last < 3000) {
                return false;
            }
        }

        lastRequest.put(user, now);
        return true;
    }

    private void sleep(int i) {
        try {
            Thread.sleep(1500L * (i + 1));
        } catch (InterruptedException ignored) {}
    }
}