package com.example.live_video.vo;

import lombok.Data;

@Data
public class QuizProblem {
    private boolean isSelection;
    private String problem;
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;  // A/B/C/D or true/false

    public QuizProblem(boolean isSelection, String problem, String a, String b, String c, String d, String answer) {
        this.isSelection = isSelection;
        this.problem = problem;
        A = a;
        B = b;
        C = c;
        D = d;
        this.answer = answer;
    }
}
