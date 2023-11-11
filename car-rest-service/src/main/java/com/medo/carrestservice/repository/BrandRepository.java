package com.medo.carrestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medo.carrestservice.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
