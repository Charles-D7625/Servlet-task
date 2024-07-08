package com.example.classes;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private boolean available;
    private int orderId;
}
