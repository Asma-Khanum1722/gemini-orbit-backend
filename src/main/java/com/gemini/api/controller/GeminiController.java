package com.gemini.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.api.DTO.GeminiRequest;
import com.gemini.api.DTO.GeminiResponse;
import com.gemini.api.service.GeminiService;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {
    
    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService){
        this.geminiService=geminiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<GeminiResponse> ask(@RequestBody GeminiRequest request ){
        return ResponseEntity.ok(geminiService.askGemini(request.prompt()));
    }

}
