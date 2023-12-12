package com.example.lab9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData {
    private int statusCode;
    private String message;
    private Object data;
}
