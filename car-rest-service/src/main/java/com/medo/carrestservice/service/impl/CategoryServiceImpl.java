package com.medo.carrestservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Category;
import com.medo.carrestservice.repository.CategoryRepository;
import com.medo.carrestservice.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        log.debug("Category saved {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        log.debug("{} Categories in the list", categoryList.size());
        return categoryList;
    }

    @Override
    public Category getCategoryById(long categoryId) throws ServiceException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ServiceException(NOT_FOUND));
        log.debug("Category under id=({}) was returned", categoryId);
        return category;
    }

    @Override
    public Category updateCategory(Category category, long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setCategoryName(category.getCategoryName());

        saveCategory(existingCategory);
        log.debug("Category under id=({}) was updated {}", categoryId, existingCategory);

        return existingCategory;
    }

    @Override
    public void deleteCategory(long categoryId) {
        getCategoryById(categoryId);
        categoryRepository.deleteById(categoryId);
        log.debug("Category under id=({}) was deleted", categoryId);
    }

}
