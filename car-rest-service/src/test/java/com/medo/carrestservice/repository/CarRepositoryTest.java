package com.medo.carrestservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.medo.carrestservice.model.Car;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CarRepositoryTest {

    private static final String TEST_BRAND = "BMW";
    private static final String TEST_CATEGORY = "categ_1";
    private static final String TEST_MODEL_OPTION_ONE = "K_1";
    private static final String TEST_MODEL_OPTION_TWO = "B_1";
    private static final int TEST_MIN_YEAR = 1900;
    private static final int TEST_MAX_YEAR = 2024;
    private static final Pageable PAGEABLE = PageRequest.ofSize(100);

    @Autowired
    private CarRepository carRepository;

    @Test
    void testCarRepository_findAllByBrandName_shouldReturnValidList() {
        List<Car> testCarlist = carRepository.findAllByBrandName(TEST_BRAND, PAGEABLE);

        testCarlist.forEach(car -> {
            assertEquals(TEST_BRAND, car.getModel().getBrand().getBrandName());
        });
    }

    @Test
    void testCarRepository_findAllByBrandName_shouldReturnSortedList() {
        List<Car> testCarlist = carRepository.findAllByBrandName(TEST_BRAND, PAGEABLE);

        assertThat(testCarlist).isSortedAccordingTo(
                (o1, o2) -> (o1.getModel().getModelName().compareTo(o2.getModel().getModelName())));
    }

    @Test
    void testCarRepository_findAllByCategoryAndModelNames_shouldReturnValidList() {
        List<Car> testCarlist = carRepository.findAllByCategoryAndModelNames(TEST_CATEGORY, TEST_MODEL_OPTION_ONE, PAGEABLE);

        testCarlist.forEach(car -> {
            assertEquals(TEST_CATEGORY, car.getCategory().getCategoryName());
            assertEquals(TEST_MODEL_OPTION_ONE, car.getModel().getModelName());
        });
    }

    @Test
    void testCarRepository_findAllByCategoryAndModelNames_shouldReturnSortedList() {
        List<Car> testCarlist = carRepository.findAllByCategoryAndModelNames(TEST_CATEGORY, TEST_MODEL_OPTION_ONE, PAGEABLE);

        assertThat(testCarlist).isSortedAccordingTo((o1, o2) -> o1.getManufacturedYear() - o2.getManufacturedYear());
    }

    @Test
    void testCarRepository_findAllByModelNameAndMinMaxYear_shouldReturnValidList() {
        List<Car> testCarlist = carRepository.findAllByModelNameAndMinMaxYear(TEST_MODEL_OPTION_TWO, TEST_MIN_YEAR,
                TEST_MAX_YEAR, PAGEABLE);

        testCarlist.forEach(car -> {
            assertEquals(TEST_MODEL_OPTION_TWO, car.getModel().getModelName());
            assertTrue(car.getManufacturedYear() >= 1900);
            assertTrue(car.getManufacturedYear() <= 2024);
        });
    }

    @Test
    void testCarRepository_findAllByModelNameAndMinMaxYear_shouldReturnSortedList() {
        List<Car> testCarlist = carRepository.findAllByModelNameAndMinMaxYear(TEST_MODEL_OPTION_TWO, TEST_MIN_YEAR,
                TEST_MAX_YEAR, PAGEABLE);

        assertThat(testCarlist).isSortedAccordingTo((o1, o2) -> o2.getManufacturedYear() - o1.getManufacturedYear());
    }
}
