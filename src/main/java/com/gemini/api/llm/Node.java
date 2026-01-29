package com.gemini.api.llm;

public class Node {
    
    private String id;
    private String label;
    private String details;

    public Node(){}

    public String getId(){
        return id;
    }

    public String getLabel(){
        return label;
    }

    public String getDetails(){
        return details;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setLabel(String label){
        this.label=label;
    }

    public void setDetails(String details){
        this.details=details;
    }

}
