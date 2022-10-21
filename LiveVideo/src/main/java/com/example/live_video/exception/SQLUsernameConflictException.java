package com.example.live_video.exception;

public class SQLUsernameConflictException extends RuntimeException {
    public SQLUsernameConflictException(){
        super("Duplicate Username in database when register");
    }
}
