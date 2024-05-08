package ru.itmo.databases;

import ru.itmo.databases.Author;
import ru.itmo.databases.C3P0Pool;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class AuthorDao {
    //будет отправляться запрос субд на тему автора
    public boolean createTable(){
        String createSql = "CREATE TABLE IF NOT EXISTS tb_authors (" +
                "id SERIAL PRIMARY KEY, " +
                "unique_name VARCHAR(50) NOT NULL, " +
                "registered_at DATE DEFAULT CURRENT_DATE NOT NULL, " +
                "is_active BOOLEAN DEFAULT TRUE NOT NULL)";

        //если IF NOT EXISTS то если ест уже табл, то сервер выкинет предупреждение,
        // а если без IF NOT EXISTS, то будет ошибка
        // ; для разделения запросов

        try {
            Class.forName("org.postgresql.Driver"); //подгружаем драйвер, все остальное сделают библиотеки
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // import java.sql.*

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/dbauthor",
                "jjd-user",
                "Za123"
        )){
            // "conn str" строка для подключения
            try (Statement statement = connection.createStatement()){
                //Statement тип данных, объект, чтобы выполнить запрос
                statement.executeUpdate(createSql);
                return true;
                // executeUpdate метод для создания, обновления, удаления таблиц или записей
                // не предполагает получение данных, для не SELECT запросов
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
    //                "VALUES (" + author.getUniqueName() + ", " + author.isActive() + ")";
    //исполь-ть конкатенацию строк здесь не хорошо

    public int insert01(Author author){
        String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (?, ?)"; // ? вместо типов данных здесь

        try (Connection connection = C3P0Pool.getConnection()){  //этот конекшен не закрыается, а возвращ-ся в пул, этот конекшен как и выше, разница в источнике
            try (PreparedStatement ps = connection.prepareStatement(insertSql)){
                ps.setString(1, author.getUniqueName()); //вместо первого ? строка с уникальным именем автора
                ps.setBoolean(2, author.isActive());  // вместо второго ?
                return ps.executeUpdate(); //возвращает кол-во строк (созданных)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] insert(List<Author> authors){
        String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (?, ?)";

        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(insertSql)){
                for (Author author : authors) {
                    ps.setString(1, author.getUniqueName());
                    ps.setBoolean(2, author.isActive());
                    ps.addBatch();
                }
                return ps.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //запрос на обновление данных в таблице
    public int update(Author author){
        String updateSql = "UPDATE tb_authors SET is_active = ? " +
                "WHERE unique_name = ?";

        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(updateSql)){
                ps.setBoolean(1, author.isActive());
                ps.setString(2, author.getUniqueName());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //запрос на получение уник.имени автора
    public Author getByUniqueName(String uniqueName){
        String selectSql = "SELECT id, unique_name, registered_at AS registered, is_active " +
                "FROM tb_authors " +
                "WHERE unique_name = ?";
        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setString(1, uniqueName);
                ResultSet resultSet = ps.executeQuery(); // итог получения запрос, когда закрыв-ся соединение или возвращ-ся в пул
                if (resultSet.next()) { // if потому что у нас точно 1 запись, мы знаем, если не знаем сколько тогда while вместо if
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered", LocalDate.class)); // в "" пишим имя столбца из результирующей вирт таблицы (псевдоним,к-рый мы писали)
                    //Boolean b = resultSet.getBoolean("is_active"); // это, чтобы точно не null, если есть хоть малый шанс, что там null
                    author.setActive(resultSet.getBoolean("is_active"));
                    return author;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    // это для выборки в рамках одного конкретного запроса registered_at AS registered, данные будут браться
    // из столбца registered_at и записываться в registered, это псевдоним. В ответ создается виртуальная табл,
    // registered будет наз-ся в этой вирт.таблице


    //запрос на получение всех авторов
    // (если много, делать не по миллиону, а частичное извлечение, напр по 60)
    public List<Author> allAuthors(){
        String selectSql = "SELECT id, unique_name, registered_at, is_active " +
                "FROM tb_authors WHERE is_active = true";
        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Author> allAuthors = null;
                while (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered_at", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    allAuthors.add(author);
                }
                return allAuthors;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Author> authorsWithoutNotes(){
        String selectSqlLeftJoin = "SELECT id, unique_name, registered_at " +
                "FROM tb_authors " +
                "LEFT JOIN tb_notes " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.author_id IS NULL"; //получим тех, у кого нет заметок, это из результатирующей таблицы
        return null;
    }
    // и трай подключение здесь д.б.



}
