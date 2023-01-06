package com.example.live_video.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizProblem {
    private boolean isSelection;
    private String problem;
    @JsonProperty("selectionA")
    private String A;
    @JsonProperty("selectionB")
    private String B;
    @JsonProperty("selectionC")
    private String C;
    @JsonProperty("selectionD")
    private String D;
    private String answer;  // A/B/C/D or true/false
}
