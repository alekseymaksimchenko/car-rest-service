package com.medo.carrestservice.service;

import java.util.List;

import com.medo.carrestservice.model.Brand;

public interface BrandService {

    Brand saveBrand(Brand brand);

    List<Brand> getAllBrands();

    Brand getBrandById(long brandId);

    Brand updateBrand(Brand brand, long brandId);

    void deleteBrand(long brandId);

}
