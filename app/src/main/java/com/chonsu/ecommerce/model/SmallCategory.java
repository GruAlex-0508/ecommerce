package com.chonsu.ecommerce.model;

import java.util.List;

public class SmallCategory {

    private final String id;
    private final String name;
    private final List<Category> categories;

    public SmallCategory(String id, String name, List<Category> categories) {

        this.id = id;
        this.name = name;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }
}