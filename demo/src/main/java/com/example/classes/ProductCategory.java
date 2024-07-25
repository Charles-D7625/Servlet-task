package com.example.classes;

import java.util.List;

import com.example.enums.CategoryType;

import lombok.Data;

@Data
public class ProductCategory {

    private int id;
    private String name;
    private CategoryType type;
    private List<Product> products;
}
