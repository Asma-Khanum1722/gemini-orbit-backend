package com.gemini.api.service;

import org.springframework.stereotype.Service;

import com.gemini.api.DTO.GeminiResponse;
import com.gemini.api.client.GeminiClient;

@Service
public class GeminiService {
    
    private final GeminiClient geminiClient;

    public GeminiService(GeminiClient geminiClient){
        this.geminiClient=geminiClient;
    }

    public GeminiResponse askGemini(String prompt){
        String response=geminiClient.generate(prompt);
        return new GeminiResponse(response);
    }

}
