package com.gemini.api.llm;

public class Edge {
    
    private String from;
    private String  to;

    public Edge(){}

    public String getFrom(){
        return from;
    }

    public String getTo(){
        return to;
    }

    public void setFrom(String from){
        this.from=from;
    }

    public void setTo(String to){
        this.to=to;
    }
}
