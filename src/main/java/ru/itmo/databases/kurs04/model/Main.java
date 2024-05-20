package ru.itmo.databases.kurs04.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itmo.databases.jpa.dao.CompetitionDao;
import ru.itmo.databases.jpa.model.Competition;
import ru.itmo.databases.kurs04.dao.*;

import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("competitions");
        EntityManager manager = factory.createEntityManager();

        Country country01 = new Country(1, "Russia");
        Country country02 = new Country(1, "Georgia");

        Mountain mountain01 = new Mountain(1,"Казбек", country01, 5000);
        Climber climber01 = new Climber(1, "Ivan", "Petrov", "79900331821",
                "petrov@gmail.com", LocalDate. of(2024, Month.FEBRUARY, 10));
        Group group01 = new Group(climber01, mountain01, "Ivanov Sergey Ivanovich",
                LocalDate. of(2024, Month.AUGUST, 15), 10, 1000);


        ClimberDao climberDao = new ClimberDao(manager);
        climberDao.update(climber01);

        CountryDao countryDao = new CountryDao(manager);
        countryDao.update(country01);

        GroupDao groupDao = new GroupDao(manager);
        groupDao.update(group01);

        MountainDao mountainDao = new MountainDao(manager);
        mountainDao.update(mountain01);

        climberDao.getNoClimb();

        manager.close();
        factory.close();


    }
}