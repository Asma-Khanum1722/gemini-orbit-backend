package com.gemini.api.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.api.DTO.GeminiResponse;
import com.gemini.api.client.GeminiClient;
import com.gemini.api.llm.GraphResponse;
import com.gemini.api.exception.GeminiException;
import com.gemini.api.validator.GraphValidator;
import com.gemini.api.mapper.GraphMapper;

@Service
public class GeminiService {
    
    private final GeminiClient geminiClient;
    private final ObjectMapper objectMapper;

    public GeminiService(GeminiClient geminiClient, ObjectMapper objectMapper){
        this.geminiClient=geminiClient;
        this.objectMapper=objectMapper;
    }

    public GeminiResponse askGemini(String prompt){
      String editedPrompt=buildGraphPrompt(prompt);
      String response=geminiClient.generate(editedPrompt);
      GraphResponse graph;
      try{
        graph=objectMapper.readValue(response, GraphResponse.class);
      }
      catch(Exception e){
        throw new GeminiException("Gemini response is not valid JSON. Raw response: ", e);
      }

      GraphValidator.validate(graph);
      return GraphMapper.mapResponse(graph);
    }


    public GeminiResponse processPdf(MultipartFile file)throws Exception{
        String extractedText=extractText(file);

        if(extractedText==null||extractedText.isBlank()){
            throw new IllegalArgumentException("The provided PDF is empty or could not be read.");
        }

        String cleanText=extractedText.replaceAll("\\s+", " ");
        cleanText=cleanText.substring(0, Math.min(cleanText.length(), 12000));
        String prompt = buildGraphPrompt(cleanText);
        String response=geminiClient.generate(prompt);
        GraphResponse graph;
        
        try{
          graph=objectMapper.readValue(response, GraphResponse.class);
        }
        catch(Exception e){
          throw new GeminiException("Gemini response is not valid JSON. Raw response: " + response, e);
        }
        
        GraphValidator.validate(graph);
        return GraphMapper.mapResponse(graph);

    }



    private String buildGraphPrompt(String text) {
        return """
You are a system that converts documents into graph data for visualization.

TASK:
Analyze the following document and extract important concepts and their relationships.

DOCUMENT:
%s

OUTPUT RULES (STRICT):
- Return ONLY valid JSON
- Do NOT include explanations or markdown
- Do NOT include comments
- Do NOT wrap JSON in backticks
- Follow the output format EXACTLY

OUTPUT FORMAT:
{
  "nodes": [
    {
      "id": "string",
      "label": "string",
      "details": "string"
    }
  ],
  "edges": [
    {
      "from": "string",
      "to": "string"
    }
  ]
}

      NODE RULES:
      - Each node id must be unique and stable
      - Use short numeric or lowercase string ids
      - Label must be human-readable
      - The details field must:
      - Start with a short heading (e.g., Overview, Definition, Function)
      - Contain a concise explanation
      - Use newline characters (\\n) for formatting
      - Use bullet points prefixed with "*"

      EDGE RULES:
      - from and to must reference existing node ids
      - Create an edge only when a clear relationship exists

      GRAPH QUALITY RULES:
      - Prefer clarity over quantity
      - Avoid duplicate or overlapping nodes
      - Extract only the most important concepts
      - Keep the graph concise and meaningful
      """.formatted(text);
    }

    private String extractText(MultipartFile file) throws Exception{
        try(PDDocument document=PDDocument.load(file.getInputStream())){
            PDFTextStripper stripper=new PDFTextStripper();
            return stripper.getText(document);
        }
    }

}
