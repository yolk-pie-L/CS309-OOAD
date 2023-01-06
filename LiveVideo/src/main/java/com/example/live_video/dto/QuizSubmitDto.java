package com.example.live_video.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitDto {
    long quizId;
    List<String> choice;
}
