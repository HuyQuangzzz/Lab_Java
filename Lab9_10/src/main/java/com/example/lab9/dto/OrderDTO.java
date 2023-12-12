package com.example.lab9.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrderDTO {
    private Long orderNumber;
    private Double totalSellingPrice;
    private List<Long> ids;
    private Long userId;
}
