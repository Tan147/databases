package ru.itmo.databases.jpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_competition") // можно явно указать имя таблицы
public class Competition {   //конкурс
    @Id
    private int id; //поле отмеченное @Id станет первичным ключом на уровне базы

    //transient - если нам не нужно это поле в таблице

    @Column(name = "title", unique = true, nullable = false, updatable = false, length = 300)
    private String title; // название конкурса
    // @Column() - если хотим сообщить орму доп.инфу по столбцу, задали явно имя и уникальность
    //nullable = false) - зн-ия по столбцу не м.б. null
    //insertable = false или updatable = false - значит это поле не участвует при формировании апдейт запроса
    // или инсерт запроса соответственно

    //String это будет тип в БД варчар и 255 размер
    // length = 300 - длина для текста станет 300

    @Column(name = "label", columnDefinition = "jsonb", nullable = false)
    private String label;
    //columnDefinition - можем описать столбец на языке sql, указываем тип jsonb в БД и нот нал
    //"jsonb NOT NULL" или тоже самое  "jsonb", nullable = false

    @OneToMany(mappedBy = "competition")
    private List<Comment> comments = new ArrayList<>(); //List/Set с обязат.инициализацией

    @ManyToMany
    @JoinTable(name = "tb_competition_students",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();


}
// если у нас будет много (миллион) записей в  List<Comment>, то тогда двунаправленную свзяь не делать,
// тогда писать самост-но запрос на извлечение комментария, но не всех записей, которые есть в таблице

//  @OneToMany - по умолч стратегия извлечения ленивая LAZY

// orphanRemoval - если удалим что-то из кода, то тогда удалится при обновлении и из таблицы,иногда это удобно, иногда опасно

//@OneToMany(mappedBy = "competition") - список с коментами формир-ся на основе связи в доп.столбце, название
// должно совпадать со св-вом  private Competition competition

/*
если нам не нужен сам объект а нужен только ид, тогда
    @OneToMany
    @JoinColumn(name = "competition_id", insertable = false)
    private List<Comment> comments = new ArrayList<>();
а для competition тогда вместо того, что есть сейчас:
@Column(name = "competition_id")
    private int competitionID;
 */
/*
@ManyToMany - создастся новая таблица, связь по ид

joinColumns - про класс, который физически находится
inverseJoinColumns - с другой стороны, про класс, который в списке указан
 */






