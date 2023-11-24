package com.medo.carrestservice.service;

import java.util.List;

import com.medo.carrestservice.model.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    List<Category> getAllCategory();

    Category getCategoryById(long categoryId);

    Category updateCategory(Category category, long categoryId);

    void deleteCategory(long categoryId);

}
