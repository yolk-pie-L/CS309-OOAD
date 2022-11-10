package com.example.live_video.entity;

public enum UserType {
    Administrator("管理员"), Student("学生"), Teacher("教师");

    final String Chinese;

    UserType(String Chinese) {
        this.Chinese = Chinese;
    }

    public String getChinese() {
        return Chinese;
    }
}
