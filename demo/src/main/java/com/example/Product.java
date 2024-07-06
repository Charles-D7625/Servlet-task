package com.example;


public class Product {

    public int id;
    public String name;
    public double price;
    public int quantity;
    public boolean available;

    public Product() {

    }

    public Product(int id, String name, double price, int quantity, boolean available) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }
}
