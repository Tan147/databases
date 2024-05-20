package ru.itmo.databases.jpa.examples;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass // для наследования не Entity классов
class Base {
    @Id
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enable;
}

@Entity
@DiscriminatorColumn(name = "ANIMAL_TYPE"
        /*,discriminatorType = DiscriminatorType.INTEGER*/)
/*abstract*/ class Animal extends Base{ // name price cat_prop dog_prop DTYPE
    private String name;
    private int price;
}

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("cat")
@Entity
class Cat extends Animal{ } // animal_id

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("dog")
@Entity
class Dog extends Animal{ } // animal_id