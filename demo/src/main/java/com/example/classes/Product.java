package com.example.classes;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private boolean available;
    private int orderId;
}
