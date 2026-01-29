package com.gemini.api.exception;

public class GeminiException extends RuntimeException{
    
    public GeminiException(){
        super();
    }

    public GeminiException(String msg){
        super(msg);
    }

    public GeminiException(String msg, Exception e){
        super(msg, e);
    }

}
