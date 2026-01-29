package com.gemini.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gemini.api.DTO.GeminiApiRequest;

@Service
public class GeminiClient {

    private final WebClient webClient;
    private final String apiKey;

    public GeminiClient(
            WebClient.Builder builder,
            @Value("${gemini.api.url}") @NonNull String url,
            @Value("${gemini.api.key}") String apiKey
    ) {
        this.apiKey = apiKey;
        this.webClient = builder
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String generate(String prompt) {

        GeminiApiRequest body = new GeminiApiRequest(
                List.of(
                        new GeminiApiRequest.Content(
                                List.of(new GeminiApiRequest.Part(prompt))
                        )
                )
        );

        return webClient.post()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("key", apiKey)
                                .build()
                )
                .bodyValue(body)
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                .map(err -> new RuntimeException("Gemini API error: " + err))
                )
                .bodyToMono(JsonNode.class)
                .map(this::extractText)
                .block();
    }

    private String extractText(JsonNode json) {
        return json
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
    }
}