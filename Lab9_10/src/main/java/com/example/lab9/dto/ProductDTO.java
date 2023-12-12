package com.example.lab9.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private Long code;
    private String name;
    private String illustration;
    private Double price;
    private String description;
    private List<Long> orders;
}
