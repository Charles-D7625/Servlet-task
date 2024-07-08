package com.example.classes;

import java.util.List;

import com.example.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDetail {

    private int id;
    private OrderStatus orderStatus;
    List<Product> products;
    private int totalAmount;
}
