package com.gemini.api.llm;

import java.util.List;

public class GraphResponse {
    private List<Node> nodes;
    private List<Edge> edges;

    public GraphResponse(){}

    public List<Node> getNodes(){
        return nodes;
    }

    public List<Edge> getEdges(){
        return edges;
    }

    public void setNodes(List<Node> nodes){
        this.nodes=nodes;
    }

    public void setEdges(List<Edge> edges){
        this.edges=edges;
    }
}
