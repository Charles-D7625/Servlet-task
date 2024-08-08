package com.example.dto;

import com.example.enums.CategoryType;

import lombok.Data;

@Data
public class ProductCategoryDTO {

    private int id;
    private String name;
    private CategoryType type;
}