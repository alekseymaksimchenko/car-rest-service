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
@Table(name = "models", schema = "car_service")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "model_name")
    private String modelName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ToStringExclude
    private Brand brand;

    public Model(String modelName, Brand brand) {
        this.modelName = modelName;
        this.brand = brand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, id, modelName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Model other = (Model) obj;
        return Objects.equals(brand, other.brand) && id == other.id && Objects.equals(modelName, other.modelName);
    }

}
