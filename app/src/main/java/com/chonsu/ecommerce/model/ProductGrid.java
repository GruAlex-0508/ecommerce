package com.chonsu.ecommerce.model;

public class ProductGrid {

    private int id;
    private String name;
    private String subtitle;
    private double price;
    private int imageResId;
    private boolean isNew;
    private boolean isFavorite;

    public ProductGrid(int id, String name, String subtitle, double price, int imageResId, boolean isNew, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.subtitle = subtitle;
        this.price = price;
        this.imageResId = imageResId;
        this.isNew = isNew;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
