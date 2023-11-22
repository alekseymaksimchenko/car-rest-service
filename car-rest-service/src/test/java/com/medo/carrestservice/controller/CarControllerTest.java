package com.medo.carrestservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Car;
import com.medo.carrestservice.service.CarService;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @MockBean
    CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @Test
    void testCarController_getAllCars_shouldReturnValidResponceEntity() throws Exception {
        List<Car> expected = List.of(new Car("testNumb1", 2024, null, null), new Car("testNumb2", 2024, null, null));
        when(carService.getAllCars()).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private/cars")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "serialNumber": "testNumb1",
                                "manufacturedYear": 2024
                            },
                            {
                                "serialNumber": "testNumb2",
                                "manufacturedYear": 2024
                            }
                        ]
                         """));
    }

    @Test
    void testCarController_getAllCars_shouldCallServiceOnce() {
        when(carService.getAllCars()).thenReturn(List.of());

        carController.getAllCars();

        verify(carService, times(1)).getAllCars();
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_getAllCarsAccordingToBrand_shouldReturnValidResponceEntity() throws Exception {
        List<Car> expected = List.of(new Car("testNumb1", 2024, null, null));
        when(carService.getAllCarsAccordingToBrand(anyString(), any(Pageable.class))).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/cars/search/brands?brandName=brandName")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "serialNumber": "testNumb1",
                                "manufacturedYear": 2024
                            }
                        ]
                         """));
    }

    @Test
    void testCarController_getAllCarsAccordingToBrand_shouldCallServiceOnce() {
        when(carService.getAllCarsAccordingToBrand(anyString(), any(Pageable.class))).thenReturn(List.of());

        carController.getAllCarsAccordingToBrand("test");

        verify(carService, times(1)).getAllCarsAccordingToBrand(anyString(), any(Pageable.class));
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_getAllByCategoryAndModelNames_shouldReturnValidResponceEntity() throws Exception {
        List<Car> expected = List.of(new Car("testNumb1", 2024, null, null));
        when(carService.getAllByCategoryAndModelNames(anyString(), anyString(), any(Pageable.class))).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/cars/search/categorys/models?categoryName=categoryName&modelName=modelName")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "serialNumber": "testNumb1",
                                "manufacturedYear": 2024
                            }
                        ]
                         """));
    }

    @Test
    void testCarController_getAllByCategoryAndModelNames_shouldCallServiceOnce() {
        when(carService.getAllByCategoryAndModelNames(anyString(), anyString(), any(Pageable.class))).thenReturn(List.of());

        carController.getAllByCategoryAndModelNames("test", "test");

        verify(carService, times(1)).getAllByCategoryAndModelNames(anyString(), anyString(), any(Pageable.class));
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_getAllByModelNameAndMinMaxYear_shouldReturnValidResponceEntity() throws Exception {
        List<Car> expected = List.of(new Car("testNumb1", 2024, null, null));
        when(carService.getAllByModelNameAndMinMaxYear(anyString(), anyInt(), anyInt(), any(Pageable.class))).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/cars/search/models/years?modelName=modelName&minYear=1900&maxYear=2024")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "serialNumber": "testNumb1",
                                "manufacturedYear": 2024
                            }
                        ]
                         """));
    }

    @Test
    void testCarController_getAllByModelNameAndMinMaxYear_shouldCallServiceOnce() {
        when(carService.getAllByModelNameAndMinMaxYear(anyString(), anyInt(), anyInt(), any(Pageable.class))).thenReturn(List.of());

        carController.getAllByModelNameAndMinMaxYear("test", 1, 1);

        verify(carService, times(1)).getAllByModelNameAndMinMaxYear(anyString(), anyInt(), anyInt(), any(Pageable.class));
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_getCarById_shouldReturnValidResponceEntity() throws Exception {
        Car car = new Car("testNumb3", 2024, null, null);
        when(carService.getCarById(anyLong())).thenReturn(car);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/cars/{id}", anyLong())
                    .with(jwt()))
                    .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                            {
                                "serialNumber": "testNumb3",
                                "manufacturedYear": 2024
                            }
                         """));
    }

    @Test
    void testCarController_getCarById_shouldFail_whenInvalidId() throws Exception {
        long id = 1L;
        when(carService.getCarById(id)).thenThrow(new ServiceException(anyString()));

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/cars/{id}", id)
                    .with(jwt()))
                    .andExpectAll(
                status().isNotFound());
    }

    @Test
    void testCarController_getCarById_shouldCallServiceOnce() {
        Car car = new Car("testNumb3", 2024, null, null);
        when(carService.getCarById(anyLong())).thenReturn(car);

        carController.getCarById(anyLong());

        verify(carService, times(1)).getCarById(anyLong());
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_createCar_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(post("/car-rest-service/api/v1/private-scoped/cars")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "id": 3,
                                "serialNumber": "testNumb3",
                                "manufacturedYear": 2024
                            }
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void testCarController_createCar_shouldCallServiceOnce() {
        Car car = new Car("testNumb3", 2024, null, null);
        carController.saveCar(car);

        verify(carService, times(1)).saveCar(car);
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_updateCar_shouldReturnValidResponceEntity() throws Exception {
        long id = 1L;
        mockMvc.perform(put("/car-rest-service/api/v1/private-scoped/cars/{id}", id)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "id": 3,
                                "serialNumber": "testNumb3",
                                "manufacturedYear": 2024
                            }
                         """))
                .andExpect(status().isOk());
    }

    @Test
    void testCarController_updateCar_shouldCallServiceOnce() {
        Car car = new Car("testNumb3", 2024, null, null);
        long id = car.getId();
        carController.updateCar(id, car);

        verify(carService, times(1)).upadateCar(car, id);
        verifyNoMoreInteractions(carService);
    }

    @Test
    void testCarController_deleteCar_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(delete("/car-rest-service/api/v1/private-scoped/cars/{id}", anyLong())
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
                content().string("Car was successfuly deleted!"));
    }

    @Test
    void testCarController_deleteCar_shouldCallServiceMethodsOnce() {
        carController.deleteCar(anyLong());

        verify(carService, times(1)).deleteCar(anyLong());
        verifyNoMoreInteractions(carService);
    }

}
