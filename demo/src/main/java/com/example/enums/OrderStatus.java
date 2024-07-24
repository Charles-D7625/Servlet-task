package com.example.enums;

public enum OrderStatus {

    ORDER_ACCEPTED ("Order is accepted"),
    ORDER_COOKING ("Order is cooking"),
    ORDER_READY ("Order is ready"),
    ORDER_DECLINE ("Order is decline");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static OrderStatus fromString(String text) {

        for (OrderStatus os : OrderStatus.values()) {
            if (os.status.equalsIgnoreCase(text.trim())) {
                return os;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
