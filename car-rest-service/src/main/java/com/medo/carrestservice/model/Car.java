package com.medo.carrestservice.model;

import java.util.Objects;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "cars", schema = "car_service")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "car_serial_number")
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

    @Override
    public int hashCode() {
        return Objects.hash(category, id, manufacturedYear, model, serialNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Car other = (Car) obj;
        return Objects.equals(category, other.category) && id == other.id && manufacturedYear == other.manufacturedYear
                && Objects.equals(model, other.model) && Objects.equals(serialNumber, other.serialNumber);
    }

}
