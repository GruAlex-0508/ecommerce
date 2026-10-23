package com.chonsu.ecommerce.model;

public class Category {

    private final String id;
    private final String name;
    private final int imageResId;

    public Category(String id, String name, int imageResId) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}