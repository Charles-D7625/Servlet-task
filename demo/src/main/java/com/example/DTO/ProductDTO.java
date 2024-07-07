package com.example.dto;

import lombok.Data;

@Data
public class ProductDTO  {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private boolean available;
}
