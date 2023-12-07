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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
@Tag(name = "Category")
public class CategoryController {

    private static final String DELETE_MASSAGE = "Category was successfuly deleted!";
    private final CategoryService categoryService;

    @Operation(
        summary = "Create category",
        description = "Saves category to Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)            
        }
        )
    @PostMapping("/private-scoped/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all categories",
        description = "Returns the list of all existing categories",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)             
        }
        )
    @GetMapping("/private/categories")
    public ResponseEntity<List<Category>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @Operation(
        summary = "Get category by Id",
        description = "Returns single category",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Category not found",
                content = @Content)            
        }
        )
    @GetMapping("/private-scoped/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @Operation(
        summary = "Update category",
        description = "Returns updated single category by id",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Category not found",
                content = @Content)            
        }
        )
    @PutMapping("/private-scoped/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long categoryId, Category category) {
        return new ResponseEntity<>(categoryService.updateCategory(category, categoryId), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete category by Id",
        description = "Deletes category single model by id from Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Category not found",
                content = @Content)            
        }
        )
    @DeleteMapping("/private-scoped/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
