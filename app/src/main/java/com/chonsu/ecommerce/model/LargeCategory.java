package com.chonsu.ecommerce.model;

import java.util.List;

public class LargeCategory {

    private final String id;
    private final String name;
    private final int imageResId;
    private final List<SmallCategory> smallCategories;

    public LargeCategory(String id, String name, int imageResId, List<SmallCategory> smallCategories) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.smallCategories = smallCategories;
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

    public List<SmallCategory> getSmallCategories() {
        return smallCategories;
    }
}