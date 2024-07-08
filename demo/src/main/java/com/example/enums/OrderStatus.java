package com.example.enums;

public enum OrderStatus {

    ORDER_TAKEN ("Заказ принят"),
    ORDER_COOKING ("Заказ готовится"),
    ORDER_READY ("Заказ готов"),
    ORDER_DECLINE ("Заказ отменен");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
