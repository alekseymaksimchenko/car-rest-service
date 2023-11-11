package com.medo.carrestservice.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
    private Model model;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Car() {
    }

    public Car(long id, String serialNumber, int manufacturedYear, Model model, Category category) {
        super();
        this.id = id;
        this.serialNumber = serialNumber;
        this.manufacturedYear = manufacturedYear;
        this.model = model;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getManufacturedYear() {
        return manufacturedYear;
    }

    public void setManufacturedYear(int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    @Override
    public String toString() {
        return "Car [id=" + id + ", serialNumber=" + serialNumber + ", manufacturedYear=" + manufacturedYear
                + ", model=" + model + ", category=" + category + "]";
    }

}
