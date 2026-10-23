package com.chonsu.ecommerce.data;

import com.chonsu.ecommerce.model.LargeCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataProvider {

    private static final List<LargeCategory> largeCategories = new ArrayList<>();

    private CategoryDataProvider() {}

    public static void setLargeCategories(List<LargeCategory> categories) {
        largeCategories.clear();
        if(categories != null) largeCategories.addAll(categories);
    }

    public static List<LargeCategory> getLargeCategories() {
        return new ArrayList<>(largeCategories);
    }

    public static LargeCategory getLargeCategoryById(String largeCategoryId) {
        if (largeCategoryId == null) return null;
        for(LargeCategory largeCategory : largeCategories) {
            if (largeCategory.getId().equals(largeCategoryId)) return largeCategory;
        }
        return null;
    }

    public static boolean isInitialized() {
        return !largeCategories.isEmpty();
    }

    public static void clear() {
        largeCategories.clear();
    }
}
