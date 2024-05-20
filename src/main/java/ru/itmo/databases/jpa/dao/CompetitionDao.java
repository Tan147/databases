package ru.itmo.databases.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import ru.itmo.databases.jpa.model.Competition;

public class CompetitionDao
        extends Dao<Competition, Integer> {


    public CompetitionDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Competition competition) {
        entityManager.getTransaction().begin();
        entityManager.persist(competition); //менеджер сущностей берет соревнование под свое управление, добавляет в свою мапу
        entityManager.getTransaction().commit();
        return competition.getId();
    }

    @Override
    public void update(Competition competition) {
        entityManager.getTransaction().begin();
        entityManager.merge(competition);
        entityManager.getTransaction().commit();
    }

    @Override
    public Competition deleteById(Integer integer) {
        Competition competition = entityManager.find(Competition.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(competition);
        entityManager.getTransaction().commit();
        return competition;
    }

    @Override
    public Competition findById(Integer integer) {
        return entityManager.find(Competition.class, integer);
    }

    public Competition findByTitle(String title) {
        String nativeSql = "SELECT * FROM tb_competition where title ILIKE ?";
        String jpql = "SELECT c FROM Competition c WHERE c.title ILIKE ?";
        // c - это имя переменной, можно любое, это чтобы обращаться через нее к классу в рамках запроса

        TypedQuery<Competition> namedNativeQuery = entityManager
                .createNamedQuery("get_by_name", Competition.class);
        namedNativeQuery.setParameter(1, title);
        Competition competition01 = namedNativeQuery.getSingleResult();
        // "get_by_name" это имя запрос и над каким классом он находится - Competition.class


        TypedQuery<Competition> jpqlQuery = entityManager
                .createQuery(jpql, Competition.class);
        Competition competition02 = jpqlQuery.getSingleResult();

        //TypedQuery<Competition> nativeQuery = entityManager.createNamedQuery(nativeSql, Competition.class);
        //Competition competition03 = nativeQuery.getSingleResult();

        Query nativeQuery = entityManager.createNativeQuery(nativeSql,Competition.class);
        nativeQuery.setParameter(1, title);
        Competition competition03 = (Competition) nativeQuery.getSingleResult();

        return competition01;
    }
}
/*
Составить SELECT запросы
Получить фамилии и email альпинистов в отсортированном по фамилии виде, которые не совершали
восхождений за последний год.

По фио руководителя получить идентификаторы групп, где количество покоривших гору больше заданного значения.

Получить список групп, открытых для записи. Показать список восхождений, которые осуществлялись в заданный период времени.

Показать список названий гор, где количество покоривших гору больше заданного значения.
 */
