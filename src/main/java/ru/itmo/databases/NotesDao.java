package ru.itmo.databases;

import java.sql.*;

public class NotesDao {
    public boolean create(){ // запрос на создание таблицы с заметками
        String createSql = "CREATE TABLE IF NOT EXISTS tb_notes(" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(120) NOT NULL, " +
                "text TEXT NOT NULL, " +
                "created_at TIMESTAMPTZ NOT NULL, " +
                "author_id INTEGER NOT NULL, " +
                "CONSTRAINT fk_author_notes " +  // создали связь, имя любое придумаваем, здесь fk_author_notes,имя нужно чтобы ее потом можно было удалить
                "FOREIGN KEY (author_id) " +
                "REFERENCES tb_authors (id))";  //лучше с пробелами, а то слипнется
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = C3P0Pool.getConnection()){
            try (Statement statement = connection.createStatement()){
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // лучше хранить в TIMESTAMPTZ, чтобы не было проблем из-за часовых поясов


    // Дз - к криэйт запросу добавляем чек ("created_at TIMESTAMPTZ NOT NULL, CHECK и условие")
    // 1. CHECK на created_at - дата в прошлом
    // 2. INSERT tb_notes
    // 3. SELECT tb_notes по идентификатору
    // 4. SELECT tb_notes по идентификатору автора
    // 5. SELECT tb_notes c LIMIT и OFFSET

}
