package ru.itmo.databases.kurs04.dao;

import jakarta.persistence.EntityManager;
import ru.itmo.databases.kurs04.model.Reserve;

public class ReserveDao extends Dao<Reserve, Integer> {

    public ReserveDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Reserve reserve) {
        entityManager.getTransaction().begin();
        entityManager.persist(reserve);
        entityManager.getTransaction().commit();
        return reserve.getId();
    }

    @Override
    public void update(Reserve reserve) {
        entityManager.getTransaction().begin();
        entityManager.merge(reserve);
        entityManager.getTransaction().commit();
    }

    @Override
    public Reserve deleteById(Integer integer) {
        Reserve reserve = entityManager.find(Reserve.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(reserve);
        entityManager.getTransaction().commit();
        return reserve;
    }

    @Override
    public Reserve findById(Integer integer) {
        return entityManager.find(Reserve.class, integer);
    }

}
