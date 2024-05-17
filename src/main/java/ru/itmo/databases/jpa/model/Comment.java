package ru.itmo.databases.jpa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_comments")
public class Comment {
    @Id
    private long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;


    @ManyToOne//(fetch = FetchType.LAZY,  // будет создаваться доп столбец competition_id
                /*cascade = CascadeType.ALL*/
               // cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Comment() {
        createdAt = LocalDateTime.now();
    }


}

//@ManyToOne - по умолч будет исп-ся жадная стратегия извлечения,
// т.ке. когда запросим коммент, то получим и соревнование
// @ManyToOne(fetch = FetchType.LAZY)  - вызов ленивой стратегии, по запросу и вызова геттера и обращению к св-ву

// cascade = CascadeType.ALL, или cascade = {CascadeType.PERSIST, CascadeType.MERGE}  - если 1 сущность ссылается
// на другую, то при каскадных операциях (каскадно добавятся все его связанные св-ва)

