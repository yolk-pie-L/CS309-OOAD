package com.example.live_video.exception;

public class SQLMailConflictException extends MyException{

    public SQLMailConflictException() {
        super("The mail has been registered");
    }
}
