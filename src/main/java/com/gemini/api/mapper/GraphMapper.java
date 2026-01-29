package com.gemini.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.gemini.api.DTO.EdgeDto;
import com.gemini.api.DTO.NodeDto;
import com.gemini.api.DTO.GeminiResponse;
import com.gemini.api.llm.*;

public class GraphMapper {
    
    public static GeminiResponse mapResponse(GraphResponse graph){

        List<NodeDto> nodes=graph.getNodes().stream().map(node->new NodeDto(
            node.getId(), node.getLabel(), node.getDetails()
        )).collect(Collectors.toList());

        List<EdgeDto> edges=graph.getEdges().stream().map(edge->new EdgeDto(
            edge.getFrom(), edge.getTo()
        )).collect(Collectors.toList());

        return new GeminiResponse(nodes, edges);
    }

}
