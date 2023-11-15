package com.medo.carrestservice.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Car;
import com.medo.carrestservice.repository.CarRepository;
import com.medo.carrestservice.service.CarService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private static final String LIST_SIZE = "{} Cars in the list";
    private final CarRepository carRepository;

    @Override
    public Car saveCar(Car car) {
        log.debug("Car saved {}", car);
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> carList = carRepository.findAll();
        log.debug(LIST_SIZE, carList.size());
        return carList;
    }

    @Override
    public Car getCarById(long carId) throws ServiceException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ServiceException(NOT_FOUND));
        log.debug("Car under id=({}) was returned", carId);
        return car;
    }

    @Override
    public Car upadateCar(Car car, long carId) {
        Car existingCar = getCarById(carId);
        existingCar.setSerialNumber(car.getSerialNumber());
        existingCar.setManufacturedYear(car.getManufacturedYear());
        existingCar.setModel(car.getModel());
        existingCar.setCategory(car.getCategory());

        saveCar(existingCar);
        log.debug("Car under id=({}) was updated {}", carId, existingCar);

        return existingCar;
    }

    @Override
    public void deleteCar(long carId) {
        getCarById(carId);
        carRepository.deleteById(carId);
        log.debug("Car under id=({}) was deleted", carId);
    }

    @Override
    public List<Car> getAllCarsAccordingToBrand(String brandName, Pageable pageable) {
        List<Car> carList = carRepository.findAllByBrandName(brandName, pageable);
        log.debug(LIST_SIZE, carList.size());
        return carList;
    }

    @Override
    public List<Car> getAllByCategoryAndModelNames(String categoryName, String modelName, Pageable pageable) {
        List<Car> carList = carRepository.findAllByCategoryAndModelNames(categoryName, modelName, pageable);
        log.debug(LIST_SIZE, carList.size());
        return carList;
    }

    @Override
    public List<Car> getAllByModelNameAndMinMaxYear(String categoryName, int minYear, int maxYear, Pageable pageable) {
        List<Car> carList = carRepository.findAllByModelNameAndMinMaxYear(categoryName, minYear, maxYear, pageable);
        log.debug(LIST_SIZE, carList.size());
        return carList;
    }

}
