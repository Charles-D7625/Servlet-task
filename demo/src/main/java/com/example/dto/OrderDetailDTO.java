package com.example.dto;

import java.math.BigDecimal;

import com.example.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDetailDTO {

    private int id;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
}
