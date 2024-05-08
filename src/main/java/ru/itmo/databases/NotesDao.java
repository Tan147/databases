package ru.itmo.databases;

import ru.itmo.databases.C3P0Pool;

import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

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

    public boolean createCheck(){
        String createSql = "CREATE TABLE IF NOT EXISTS tb_notes(" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(120) NOT NULL, " +
                "text TEXT NOT NULL, " +
                "created_at TIMESTAMPTZ NOT NULL, CHECK created_at > '2024-05-07'" +
                "author_id INTEGER NOT NULL, " +
                "CONSTRAINT fk_author_notes " +
                "FOREIGN KEY (author_id) " +
                "REFERENCES tb_authors (id))";
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

    public int insert(Note note){
        String insertSql = "INSERT INTO tb_notes (title, text) " +
                "VALUES (?, ?)";
        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(insertSql)){
                ps.setString(1, note.getTitle());
                ps.setString(2, note.getText());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Note getNoteById(int id) { //получить все поля из табл авторов и заметок
        String selectSql = "SELECT " +
                "tb_authors.id AS id_author, tb_authors.unique_name, tb_authors.registered_at, tb_authors.is_active, " +
                "tb_notes.id AS id_note, tb_notes.title, tb_notes.text, tb_notes.created_at" +
                "FROM tb_authors, tb_notes " +
                "WHERE tb_authors.id = tb_notes.author_id AND " + //здесь соответствие, сопоставление
                "tb_notes.id = ?";
       /* String selectSqlInnerJoin = "SELECT " +
                "tb_authors.id AS id_author, tb_authors.unique_name, tb_authors.registered_at, tb_authors.is_active, " +
                "tb_notes.id AS id_note, tb_notes.title, tb_notes.text, tb_notes.created_at" +
                "FROM tb_authors " +
                "JOIN tb_notes " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.id = ?";

                это через Inner Join
        */

        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

                //id_author, unique_name, registered_at, is_active, id_note,title,text,created_at  будет такая табл
                if (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id_author"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered_at", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    Note note = new Note();
                    note.setId(resultSet.getInt("id_note"));
                    note.setTitle(resultSet.getString("title"));
                    note.setText(resultSet.getString("text"));
                    note.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    note.setAuthor(author);
                    return note;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    //получить названия заметок автора, у которых имя автора authorName
    public List<Note> getNotesByAuthorName(String authorName){
        String selectSql = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.unique_name = ?"; // ищется строгое соответствие с учетом регистра
        /*
        String selectSql = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.unique_name IN (?,?,?)"; // поиск в одном из перечисленных значений (тут из 3 имен)
        */

        /*
        String selectSql = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.unique_name ILIKE ?)"; // регистронезависисмый аналог LIKE, ищет по шаблону,
        // шалон задается через процент %o% - которые содержат букву о
        // о% - ищет имя, которое начианется на о
        // %о - имя заканчивается на о
        // ILIKE о - это строгое соответствие имени, т.е. имя = о (одна буква о)

         */

        return null;
    } // + трай подключение


    // если без учета регистра нужно, то можно регситро независимый поиск:
    // "WHERE lower(tb_notes.unique_name) = lower(?)";

    // есть оператор LIKE и ILIKE


}

/* студенты
ид   имя


конкурсы
ид    название    дата проведения    статус[ENUM](это перечисление)



отзывы
ид     текст    дата и вр добавления    ид конкурса    ид студента


Участие студента в конкурсе
ид студента   ид конкурса   место(1вар)  ид приза(1 вар)   ?ид участия


Призы
ид   название     место(за какое)   ид конкурса  ?ид участия(2вар)


json


если2 вар класс участие можно не создавать, субд его сама сделает
и ид участия столбец будет связан с ид студ и ид конкурса из табл участия
Т.е. если табл. хранит только связи, то в коде ее не описывают

если 1 вар, то создать класс и описать его
 */


