package ru.itmo.databases.kurs04.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ru.itmo.databases.kurs04.model.CompletedMountainClimb;

import java.time.LocalDate;
import java.util.List;


public class CompletedMountainClimbDao extends Dao<CompletedMountainClimb, Integer> {

    public CompletedMountainClimbDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(CompletedMountainClimb completedMountainClimb) {
        entityManager.getTransaction().begin();
        entityManager.persist(completedMountainClimb);
        entityManager.getTransaction().commit();
        return completedMountainClimb.getId();
    }

    @Override
    public void update(CompletedMountainClimb completedMountainClimb) {
        entityManager.getTransaction().begin();
        entityManager.merge(completedMountainClimb);
        entityManager.getTransaction().commit();
    }

    @Override
    public CompletedMountainClimb deleteById(Integer integer) {
        CompletedMountainClimb completedMountainClimb = entityManager.find(CompletedMountainClimb.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(completedMountainClimb);
        entityManager.getTransaction().commit();
        return completedMountainClimb;
    }

    @Override
    public CompletedMountainClimb findById(Integer integer) {
        return entityManager.find(CompletedMountainClimb.class, integer);
    }

    public List<Integer> groupId(String fullNameOfTheHead, int members) {
        String groupIdSql = "SELECT group_id " +
                "FROM tb_completed_mountain_climb " +
                "JOIN tb_group " +
                "ON tb_group.id = tb_completed_mountain_climb.group_id " +
                "WHERE tb_group.full_name_of_the_head =: full_name_of_the_head " +
                "AND tb_completed_mountain_climb.number_of_members >: members";
        Query query = entityManager.createNativeQuery(groupIdSql, Integer.class);
        query.setParameter("head", fullNameOfTheHead);
        query.setParameter("members", members);
        return query.getResultList();
    }

    public List<CompletedMountainClimb> getClimbByPeriod(LocalDate start, LocalDate finish) {
        String getClimbByPeriodSql = "SELECT * " +
                "FROM tb_completed_mountain_climb " +
                "WHERE start >=: start " +
                "AND finish <=: finish";
        Query query = entityManager.createNativeQuery(getClimbByPeriodSql, CompletedMountainClimb.class);
        query.setParameter("start", start);
        query.setParameter("finish", finish);
        return query.getResultList();
    }

    public List<String> getMountainName(int members) {
        String getMountainNameSql = "SELECT tb_mountain.mountain_name " +
                "FROM tb_completed_mountain_climb " +
                "JOIN tb_mountain " +
                "ON tb_mountain.id = tb_completed_mountain_climb.mountain_id " +
                "WHERE tb_completed_mountain_climb.number_of_members >: members";
        Query query = entityManager.createNativeQuery(getMountainNameSql, String.class);
        query.setParameter("numberOfMembers", members);
        return query.getResultList();
    }

}

/*
Составить SELECT запросы
 По фио руководителя получить идентификаторы групп, где количество покоривших гору больше заданного значения.

 Показать список восхождений, которые осуществлялись в заданный период времени.

 Показать список названий гор, где количество покоривших гору больше заданного значения.
 */