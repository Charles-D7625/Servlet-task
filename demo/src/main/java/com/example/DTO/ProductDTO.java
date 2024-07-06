package com.example.DTO;

import lombok.Data;

@Data
public class ProductDTO {

    public String name;
    public double price;
    public int quantity;
    public boolean available;
}
