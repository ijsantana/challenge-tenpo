package com.tenpo.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ApiErrorRepsonse {

    public ApiErrorRepsonse(){
    }

    public ApiErrorRepsonse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    private Integer code;
    private String message;
    private LocalDateTime timestamp;


}
