package ru.itmo.databases.jdbc.model;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.databases.jdbc.model.Author;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Note {
    private long id;
    private String title;
    private String text;
    private OffsetDateTime createdAt;
    private Author author; // на уровне таблиц автора не будет, а будет author_id, только его и будем хранить в табл, а не объект целиком

    //post SQL driver не умеет работать с локал DateTime
    // ор умеет работать



}
