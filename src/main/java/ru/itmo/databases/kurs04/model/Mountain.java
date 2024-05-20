package ru.itmo.databases.kurs04.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.itmo.databases.kurs04.model.Country;

@Data
@Entity
@Table(name = "tb_mountain")
public class Mountain {

    @Id
    private int id;

    @Column(name = "mountain_name", unique = true, nullable = false)
    private String mountainName;

    @Column(nullable = false)
    @OneToMany
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = false)
    private int height;

    public Mountain(int id, String mountainName, Country country, int height) {
        if (height < 100) throw new IllegalArgumentException("Высота горы не может быть меньше 100 метров");
        this.id = id;
        this.mountainName = mountainName;
        this.country = country;
        this.height = height;
    }

    public String getMountainName() {
        return mountainName;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
