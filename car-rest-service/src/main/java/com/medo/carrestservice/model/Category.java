package com.medo.carrestservice.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "categories", schema = "car_service")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "category_name")
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Category other = (Category) obj;
        return Objects.equals(categoryName, other.categoryName) && id == other.id;
    }

}
