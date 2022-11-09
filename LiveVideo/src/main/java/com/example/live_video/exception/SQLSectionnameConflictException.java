package com.example.live_video.exception;

public class SQLSectionnameConflictException extends MyException{

    public SQLSectionnameConflictException(){
        super("One course cannot have two sections with the same name");
    }
}
