package com.medo.carrestservice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medo.carrestservice.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = """
            FROM #{#entityName}
            INNER JOIN model
            INNER JOIN brand
            WHERE brandName = ?1
            ORDER BY modelName ASC
            """)
    List<Car> findAllByBrandName(String brandName, Pageable pageable);

    @Query(value = """
            FROM #{#entityName}
            INNER JOIN category
            INNER JOIN model
            WHERE categoryName = ?1 AND modelName = ?2
            ORDER BY manufacturedYear ASC
            """)
    List<Car> findAllByCategoryAndModelNames(String categoryName, String modelName, Pageable pageable);

    @Query(value = """
            FROM #{#entityName}
            INNER JOIN model
            WHERE modelName = ?1 AND manufacturedYear BETWEEN ?2 AND ?3
            ORDER BY modelName ASC, manufacturedYear DESC
            """)
    List<Car> findAllByModelNameAndMinMaxYear(String categoryName, int minYear, int maxYear, Pageable pageable);

}
