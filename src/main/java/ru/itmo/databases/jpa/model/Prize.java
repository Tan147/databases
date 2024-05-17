package ru.itmo.databases.jpa.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

@Data
@Entity
@Table(name = "tb_prizes", indexes = {
        @Index(name = "title_index", columnList = "title" /*, unique = true*/),
})
public class Prize {
    @Id
    // AUTO - JPA провайдер решает, как генерировать уникальные ID для сущности
    // SEQUENCE - используется последовательность - объект БД для генерации уникальных значений, реализуется средствами БД
    // TABLE - эмуляция последовательность в отдельной таблице, реализуется средствами ORM
    // IDENTITY стратегия- используется встроенный в БД тип данных для генерации значения первичного ключа
    @GeneratedValue(strategy = GenerationType.IDENTITY) //про автоинкремент
    //@SequenceGenerator() - посм эту @
    private int id;

    @NotNull
    @NotBlank
    //@Size(min = 3, max = 20)
    @Column(nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING) //чтобы эл-ты Перечислений на уровне табл хранились не ка цифра а как строки
    @Column(name = "place")
    private PlaceNumber placeNumber;


    //@ManyToOne
    @Positive
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

}
