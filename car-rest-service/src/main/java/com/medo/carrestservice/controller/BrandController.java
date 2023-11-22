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

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
public class BrandController {

    private static final String DELETE_MASSAGE = "Brand was successfuly deleted!";
    private final BrandService brandService;

    @PostMapping("/private-scoped/brands")
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.saveBrand(brand), HttpStatus.CREATED);
    }

    @GetMapping("/private/brands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return new ResponseEntity<>(brandService.getAllBrands(), HttpStatus.OK);
    }

    @GetMapping("/private-scoped/brands/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") long brandId) {
        return new ResponseEntity<>(brandService.getBrandById(brandId), HttpStatus.OK);
    }

    @PutMapping("/private-scoped/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable("id") long brandId, @RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.updateBrand(brand, brandId), HttpStatus.OK);
    }

    @DeleteMapping("/private-scoped/brands/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable("id") long brandId) {
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
