package ru.itmo.databases.kurs04.dao;

import jakarta.persistence.EntityManager;
import ru.itmo.databases.C3P0Pool;
import ru.itmo.databases.kurs04.model.Climber;


import java.sql.*;
import java.util.List;

public class ClimberDao extends Dao<Climber, Integer> {

    public ClimberDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Integer insert(Climber climber) {
        entityManager.getTransaction().begin();
        entityManager.persist(climber);
        entityManager.getTransaction().commit();
        return climber.getId();
    }

    @Override
    public void update(Climber climber) {
        entityManager.getTransaction().begin();
        entityManager.merge(climber);
        entityManager.getTransaction().commit();
    }

    @Override
    public Climber deleteById(Integer integer) {
        Climber climber = entityManager.find(Climber.class, integer);
        entityManager.getTransaction().begin();
        entityManager.remove(climber);
        entityManager.getTransaction().commit();
        return climber;
    }

    @Override
    public Climber findById(Integer integer) {
        return entityManager.find(Climber.class, integer);
    }

    public List<Climber> getNoClimb() {
        String getNoClimbSql = "SELECT surname, email " +
                "FROM tb_climber " +
                "WHERE last_climb < CURRENT_DATE - 365 ORDER BY surname";
        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(getNoClimbSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Climber> getNoClimb = null;
                while (resultSet.next()) {
                    Climber climber = new Climber();
                    climber.setSurname(resultSet.getString("surname"));
                    climber.setEmail(resultSet.getString("email"));
                    getNoClimb.add(climber);
                }
                return getNoClimb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

    /*
Составить SELECT запросы
Получить фамилии и email альпинистов в отсортированном по фамилии виде, которые не совершали восхождений за последний год.

 */

