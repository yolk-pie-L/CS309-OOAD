package com.example.live_video.exception;

public class SQLAssignNameConflictException extends MyException{
    public SQLAssignNameConflictException() {
        super("Assignment name should be unique in one course");
    }
}
