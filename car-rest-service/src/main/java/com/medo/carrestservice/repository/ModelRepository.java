package com.medo.carrestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medo.carrestservice.model.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {

}
