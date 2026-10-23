package com.chonsu.ecommerce.model;

public class ShippingProduct {
    private String productName;
    private String productSize;
    private String productPrice;
    private String productAmount;
    private String productImg;

    public ShippingProduct(String productName, String productSize, String productPrice, String productAmount, String productImg) {
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
