package com.example.live_video.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class ExampleForm {

    @NotBlank(message = "Message should not be null")
    String msg;

    @Length(min = 6, max = 15, message = "Message should be at least {min} characters, message should not exceed {max} characters")
    String password;

    @Range(min = 1, max = 100, message = "Age should at the range of {min} and {max}")
    Integer age;
}
