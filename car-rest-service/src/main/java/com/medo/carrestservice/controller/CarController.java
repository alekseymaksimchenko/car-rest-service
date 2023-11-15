package com.medo.carrestservice.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medo.carrestservice.model.Car;
import com.medo.carrestservice.service.CarService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private static final String DELETE_MASSAGE = "Car was successfuly deleted!";
    private static final Pageable PAGEABLE = PageRequest.ofSize(100);

    private final CarService carService;

    @PostMapping()
    public ResponseEntity<Car> saveCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.saveCar(car), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("search/brands")
    public ResponseEntity<List<Car>> getAllCarsAccordingToBrand(@RequestParam("brandName") String brandName) {
        return new ResponseEntity<>(carService.getAllCarsAccordingToBrand(brandName, PAGEABLE), HttpStatus.OK);
    }

    @GetMapping("search/categorys/models")
    public ResponseEntity<List<Car>> getAllByCategoryAndModelNames(@RequestParam("categoryName") String categoryName, 
                                                                   @RequestParam("modelName") String modelName) {
        
        return new ResponseEntity<>(carService.getAllByCategoryAndModelNames(categoryName, modelName, PAGEABLE), HttpStatus.OK);
    }

    @GetMapping("search/models/years")
    public ResponseEntity<List<Car>> getAllByModelNameAndMinMaxYear(@RequestParam("modelName") String modelName, 
                                                                    @RequestParam("minYear") int minYear, 
                                                                    @RequestParam("maxYear") int maxYear) {
        
        return new ResponseEntity<>(carService.getAllByModelNameAndMinMaxYear(modelName, minYear, maxYear, PAGEABLE), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") long carId) {
        return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") long carId, Car car) {
        return new ResponseEntity<>(carService.upadateCar(car, carId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
