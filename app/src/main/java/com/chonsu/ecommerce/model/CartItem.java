package com.chonsu.ecommerce.model;

public class CartItem {

    private int imageResId;
    private String name;
    private String size;
    private double price;
    private int quantity;

    public CartItem(int imageResId, String name, String size, double price, int quantity) {
        this.imageResId = imageResId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
