package com.tenpo.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class OperationDto {
    @JsonProperty(required = true)
    private Double number1;
    @JsonProperty(required = true)
    private Double number2;
}
