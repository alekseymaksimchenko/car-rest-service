package com.medo.carrestservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medo.carrestservice.model.Category;
import com.medo.carrestservice.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
public class CategoryController {

    private static final String DELETE_MASSAGE = "Category was successfuly deleted!";
    private final CategoryService categoryService;

    @PostMapping("/private-scoped/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @GetMapping("/private/categories")
    public ResponseEntity<List<Category>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/private-scoped/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @PutMapping("/private-scoped/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long categoryId, Category category) {
        return new ResponseEntity<>(categoryService.updateCategory(category, categoryId), HttpStatus.OK);
    }

    @DeleteMapping("/private-scoped/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
