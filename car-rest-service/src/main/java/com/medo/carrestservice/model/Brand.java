package com.medo.carrestservice.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "brands", schema = "car_service")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "brand_name")
    private String brandName;

    public Brand() {
    }

    public Brand(long id, String brandName) {
        super();
        this.id = id;
        this.brandName = brandName;
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandName, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Brand other = (Brand) obj;
        return Objects.equals(brandName, other.brandName) && id == other.id;
    }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", brandName=" + brandName + "]";
    }

}
