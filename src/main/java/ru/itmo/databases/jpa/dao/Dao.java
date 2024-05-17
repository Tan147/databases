package ru.itmo.databases.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

abstract public class Dao<T, ID> {
    protected EntityManager entityManager;
    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    abstract ID insert(T t) throws Exception;
    abstract void update(T t) throws Exception;
    abstract T deleteById(ID id) throws Exception;
    abstract T findById(ID id) throws Exception;
}
