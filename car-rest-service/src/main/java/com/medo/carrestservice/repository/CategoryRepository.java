package com.medo.carrestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medo.carrestservice.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
