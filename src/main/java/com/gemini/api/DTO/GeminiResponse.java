package com.gemini.api.DTO;

import java.util.List;

public record GeminiResponse(List<NodeDto> nodes, List<EdgeDto> edges){}
