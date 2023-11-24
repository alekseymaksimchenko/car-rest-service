package com.medo.carrestservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Car;
import com.medo.carrestservice.repository.CarRepository;

@SpringBootTest(classes = CarServiceImpl.class)
class CarServiceImplTest {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private static final Pageable PAGEABLE = PageRequest.ofSize(100);

    @Autowired
    private CarServiceImpl carServiceImpl;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private Car testCar;

    @Test
    void testCarServiceImpl_saveCar_shouldCallRepository_oneTime() {
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        carServiceImpl.saveCar(testCar);
        verify(carRepository, times(1)).save(any(Car.class));
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getAllCarsAccordingToBrand_shouldCallRepository_oneTime() {
        carServiceImpl.getAllCarsAccordingToBrand("test", PAGEABLE);

        verify(carRepository, times(1)).findAllByBrandName(anyString(), any(Pageable.class));
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getAllByCategoryAndModelNames_shouldCallRepository_oneTime() {
        carServiceImpl.getAllByCategoryAndModelNames("test", "test", PAGEABLE);

        verify(carRepository, times(1)).findAllByCategoryAndModelNames(anyString(), anyString(), any(Pageable.class));
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getAllByModelNameAndMinMaxYear_shouldCallRepository_oneTime() {
        carServiceImpl.getAllByModelNameAndMinMaxYear("test", 1, 1, PAGEABLE);

        verify(carRepository, times(1)).findAllByModelNameAndMinMaxYear(anyString(), anyInt(), anyInt(), any(Pageable.class));
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getAllCars_shouldCallRepository_oneTime() {
        carServiceImpl.getAllCars();

        verify(carRepository, times(1)).findAll();
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getCarById_shouldCallRepository_oneTime() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(testCar));

        carServiceImpl.getCarById(anyLong());
        verify(carRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void testCarServiceImpl_getCarById_shouldThrowServiceException_inCaseOfNoEntityFound() {
        Exception exception = assertThrows(ServiceException.class, () -> carServiceImpl.getCarById(1L));
        String actual = exception.getMessage();
        String expected = NOT_FOUND;

        assertEquals(expected, actual);
    }

    @Test
    void testCarServiceImpl_updateCar_shouldCallRepository_twiceInRightOrder() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(testCar));
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        carServiceImpl.upadateCar(testCar, 1L);

        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(Car.class));
        verifyNoMoreInteractions(carRepository);

        InOrder inOrder = Mockito.inOrder(carRepository);
        inOrder.verify(carRepository).findById(anyLong());
        inOrder.verify(carRepository).save(any(Car.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testCarServiceImpl_deleteCar_shouldCallRepository_twicetwiceInRightOrder() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(testCar));
        carServiceImpl.deleteCar(anyLong());

        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(carRepository);

        InOrder inOrder = Mockito.inOrder(carRepository);
        inOrder.verify(carRepository).findById(anyLong());
        inOrder.verify(carRepository).deleteById(anyLong());
        inOrder.verifyNoMoreInteractions();
    }

}
