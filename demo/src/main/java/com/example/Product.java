package com.example;

import lombok.Data;

@Data
public class Product {

    public int id;
    public String name;
    public double price;
    public int quantity;
    public boolean available;

}
