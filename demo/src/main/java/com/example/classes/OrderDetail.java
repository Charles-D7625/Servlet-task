package com.example.classes;

import java.math.BigDecimal;
import java.util.List;

import com.example.dto.ProductDTO;
import com.example.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDetail {

    private int id;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    List<ProductDTO> products;
}
