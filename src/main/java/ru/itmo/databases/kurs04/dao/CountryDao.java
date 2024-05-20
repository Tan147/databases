package ru.itmo.databases.kurs04.dao;

import jakarta.persistence.EntityManager;
import ru.itmo.databases.kurs04.model.Country;

public class CountryDao extends Dao<Country, Integer> {

    public CountryDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Country country) {
        entityManager.getTransaction().begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
        return country.getId();
    }

    @Override
    public void update(Country country) {
        entityManager.getTransaction().begin();
        entityManager.merge(country);
        entityManager.getTransaction().commit();
    }

    @Override
    public Country deleteById(Integer integer) {
        Country country = entityManager.find(Country.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(country);
        entityManager.getTransaction().commit();
        return country;
    }

    @Override
    public Country findById(Integer integer) {
        return entityManager.find(Country.class, integer);
    }
}
