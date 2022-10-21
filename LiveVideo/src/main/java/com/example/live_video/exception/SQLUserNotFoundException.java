package com.example.live_video.exception;

public class SQLUserNotFoundException extends RuntimeException{

    public SQLUserNotFoundException(){
        super("User not found in the database");
    }
}
