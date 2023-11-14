package com.medo.carrestservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Brand;
import com.medo.carrestservice.repository.BrandRepository;
import com.medo.carrestservice.service.BrandService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        super();
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        log.debug("Brand saved {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brandList = brandRepository.findAll();
        log.debug("{} Brends in the list", brandList.size());
        return brandList;
    }

    @Override
    public Brand getBrandById(long brandId) throws ServiceException{
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ServiceException(NOT_FOUND));
        log.debug("Brand under id=({}) was returned", brandId);
        return brand;
    }

    @Override
    public Brand updateBrand(Brand brand, long brandId) {
        Brand existingBrand = getBrandById(brandId);
        existingBrand.setBrandName(brand.getBrandName());

        saveBrand(existingBrand);
        log.debug("Brand under id=({}) was updated {}", brandId, existingBrand);

        return existingBrand;
    }

    @Override
    public void deleteBrand(long brandId) {
        getBrandById(brandId);
        brandRepository.deleteById(brandId);
        log.debug("Brand under id=({}) was deleted", brandId);
    }

}
