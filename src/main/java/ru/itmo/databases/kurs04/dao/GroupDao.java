package ru.itmo.databases.kurs04.dao;

import jakarta.persistence.EntityManager;
import ru.itmo.databases.C3P0Pool;
import ru.itmo.databases.kurs04.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDao extends Dao<Group, Integer> {

    public GroupDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Group group) {
        entityManager.getTransaction().begin();
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        return group.getId();
    }

    @Override
    public void update(Group group) {
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
    }

    @Override
    public Group deleteById(Integer integer) {
        Group group = entityManager.find(Group.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(group);
        entityManager.getTransaction().commit();
        return group;
    }

    @Override
    public Group findById(Integer integer) {
        return entityManager.find(Group.class, integer);
    }

    public List<Group> openGroups() {
        String openGroupsSql = "SELECT * " +
                "FROM tb_group " +
                "WHERE tb_group.group.size() < tb_group.max_members " +
                "AND CURRENT_DATE < tb_group.ascentDate";
        try (
                Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(openGroupsSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Group> openGroups = null;
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("id"));
                    openGroups.add(group);
                }
                return openGroups;
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

/*
Составить SELECT запросы
 Получить список групп, открытых для записи.
 */


