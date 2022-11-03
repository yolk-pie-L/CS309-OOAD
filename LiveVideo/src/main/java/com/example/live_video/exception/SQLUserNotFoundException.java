package com.example.live_video.exception;

public class SQLUserNotFoundException extends MyException{

    public SQLUserNotFoundException(){
        super("User not found in the database");
    }
}
