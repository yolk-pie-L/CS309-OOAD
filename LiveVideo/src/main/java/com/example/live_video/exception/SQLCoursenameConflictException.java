package com.example.live_video.exception;

public class SQLCoursenameConflictException extends MyException{

    public SQLCoursenameConflictException(){
        super("One teacher cannot have two courses with the same name");
    }

}
