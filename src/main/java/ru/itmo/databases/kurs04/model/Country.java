package ru.itmo.databases.kurs04.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_country")
public class Country {

    @Id
    private int id;

    @Column(name = "name_of_country", nullable = false, updatable = false)
    private String nameOfCountry;

    public Country(int id, String nameOfCountry) {
        this.id = id;
        this.nameOfCountry = nameOfCountry;
    }

    public String getNameOfCountry() {
        return nameOfCountry;
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
