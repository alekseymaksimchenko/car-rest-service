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

import com.medo.carrestservice.model.Brand;
import com.medo.carrestservice.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
@Tag(name = "Brand")
public class BrandController {

    private static final String DELETE_MASSAGE = "Brand was successfuly deleted!";
    private final BrandService brandService;

    @Operation(
        summary = "Create brand",
        description = "Saves brand to Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Brand.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)            
        }
        )
    @PostMapping("/private-scoped/brands")
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.saveBrand(brand), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all brands",
        description = "Returns the list of all existing brands",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Brand.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)             
        }
        )
    @GetMapping("/private/brands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return new ResponseEntity<>(brandService.getAllBrands(), HttpStatus.OK);
    }

    @Operation(
        summary = "Get brand by Id",
        description = "Returns single brand",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Brand.class)) }),
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
                description = "Brand not found",
                content = @Content)            
        }
        )
    @GetMapping("/private-scoped/brands/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") long brandId) {
        return new ResponseEntity<>(brandService.getBrandById(brandId), HttpStatus.OK);
    }

    @Operation(
        summary = "Update brand",
        description = "Returns updated single brand by id",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Brand.class)) }),
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
                description = "Brand not found",
                content = @Content)            
        }
        )
    @PutMapping("/private-scoped/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable("id") long brandId, @RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.updateBrand(brand, brandId), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete brand by Id",
        description = "Deletes model single brand by id from Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Brand.class)) }),
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
                description = "Brand not found",
                content = @Content)            
        }
        )
    @DeleteMapping("/private-scoped/brands/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable("id") long brandId) {
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
