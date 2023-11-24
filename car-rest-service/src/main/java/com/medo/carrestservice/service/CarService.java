package com.medo.carrestservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.medo.carrestservice.model.Car;

public interface CarService {

    Car saveCar(Car car);

    List<Car> getAllCars();

    Car getCarById(long carId);

    Car upadateCar(Car car, long carId);

    void deleteCar(long carId);

    List<Car> getAllCarsAccordingToBrand(String brandName, Pageable pageable);

    List<Car> getAllByCategoryAndModelNames(String categoryName, String modelName, Pageable pageable);

    List<Car> getAllByModelNameAndMinMaxYear(String categoryName, int minYear, int maxYear, Pageable pageable);

}
