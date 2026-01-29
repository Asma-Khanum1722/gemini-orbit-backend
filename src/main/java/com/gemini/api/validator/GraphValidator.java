package com.gemini.api.validator;

import java.util.Set;
import java.util.HashSet;

import com.gemini.api.llm.*;
import com.gemini.api.exception.GraphException;

public class GraphValidator {
    
    public static void validate(GraphResponse graph){

        if(graph.getNodes()==null||graph.getNodes().isEmpty()){
            throw new GraphException("Node is missing");
        }

        if(graph.getEdges()==null){
            throw new GraphException("Edges is missing");
        }

        Set<String> nodeIds=new HashSet<>();

        for(Node node: graph.getNodes()){
            if(node.getId()==null || node.getId().isBlank()){
                throw new GraphException("Id not returned with graph node");
            }
            nodeIds.add(node.getId());
        }

        for(Edge edge: graph.getEdges()){
            if(!nodeIds.contains(edge.getFrom())||!nodeIds.contains(edge.getTo())){
                throw new GraphException("Edge references non-existing node: "+edge.getFrom()+ " -> "+edge.getTo());
            }
        }

    }

}
