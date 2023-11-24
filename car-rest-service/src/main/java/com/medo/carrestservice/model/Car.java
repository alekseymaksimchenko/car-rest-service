package com.medo.carrestservice.model;

import org.apache.commons.lang3.builder.ToStringExclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cars", schema = "car_service")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "manufactured_year")
    private int manufacturedYear;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ToStringExclude
    private Model model;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToStringExclude
    private Category category;

    public Car(String serialNumber, int manufacturedYear, Model model, Category category) {
        this.serialNumber = serialNumber;
        this.manufacturedYear = manufacturedYear;
        this.model = model;
        this.category = category;
    }

}
