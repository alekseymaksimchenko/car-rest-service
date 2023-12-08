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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
@Tag(name = "Car")
public class CarController {

    private static final String DELETE_MASSAGE = "Car was successfuly deleted!";
    private static final Pageable PAGEABLE = PageRequest.ofSize(100);

    private final CarService carService;

    @Operation(
        summary = "Create car",
        description = "Saves car to Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)            
        }
        )
    @PostMapping("/private-scoped/cars")
    public ResponseEntity<Car> saveCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.saveCar(car), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all cars",
        description = "Returns the list of all existing cars",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)             
        }
        )
    @GetMapping("/private/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @Operation(
        summary = "Get all cars by Brand name",
        description = "Returns list of all cars according to provided brandy name",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),           
        }
        )
    @GetMapping("/private-scoped/cars/search/brands")
    public ResponseEntity<List<Car>> getAllCarsAccordingToBrand(@RequestParam("brandName") String brandName) {
        return new ResponseEntity<>(carService.getAllCarsAccordingToBrand(brandName, PAGEABLE), HttpStatus.OK);
    }

    @Operation(
        summary = "Get all cars by Category and Model names",
        description = "Returns list of all cars according to provided category name and model name",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),           
        }
        )
    @GetMapping("/private-scoped/cars/search/categorys/models")
    public ResponseEntity<List<Car>> getAllByCategoryAndModelNames(@RequestParam("categoryName") String categoryName,
                                                                   @RequestParam("modelName") String modelName) {

        return new ResponseEntity<>(carService.getAllByCategoryAndModelNames(categoryName, modelName, PAGEABLE), HttpStatus.OK);
    }

    @Operation(
        summary = "Get all cars by Category and Model names according to years range",
        description = "Returns list of all cars according to provided category name and model name according to inputted min and max years parameter",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),        
        }
        )
    @GetMapping("/private-scoped/cars/search/models/years")
    public ResponseEntity<List<Car>> getAllByModelNameAndMinMaxYear(@RequestParam("modelName") String modelName,
                                                                    @RequestParam("minYear") int minYear,
                                                                    @RequestParam("maxYear") int maxYear) {

        return new ResponseEntity<>(carService.getAllByModelNameAndMinMaxYear(modelName, minYear, maxYear, PAGEABLE), HttpStatus.OK);
    }

    @Operation(
        summary = "Get car by Id",
        description = "Returns single car",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
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
                description = "Car not found",
                content = @Content)            
        }
        )
    @GetMapping("/private-scoped/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") long carId) {
        return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
    }

    @Operation(
        summary = "Update car",
        description = "Returns updated single car by id",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
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
                description = "Car not found",
                content = @Content)            
        }
        )
    @PutMapping("/private-scoped/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") long carId, Car car) {
        return new ResponseEntity<>(carService.upadateCar(car, carId), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete car by Id",
        description = "Deletes model single car by id from Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Car.class)) }),
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
                description = "Car not found",
                content = @Content)            
        }
        )
    @DeleteMapping("/private-scoped/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
