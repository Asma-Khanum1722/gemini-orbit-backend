package com.gemini.api.exception;

public class GraphException extends RuntimeException {
 
    public GraphException(){
        super();
    }

    public GraphException(String msg){
        super(msg);
    }

    public GraphException(String msg, Exception e){
        super(msg, e);
    }

}
