package com.example.dto;

import com.example.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDetailDTO {

    private int id;
    private OrderStatus orderStatus;
    private double totalAmount;
}
