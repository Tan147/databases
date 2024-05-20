package ru.itmo.databases.jpa.examples;

import jakarta.persistence.*;

import java.io.Serializable;

public class CompositeKeysExample {
}

@Entity
@IdClass(EntityClassPK01.class)
class EntityClass01{
    @Id
    private int value01;
    @Id
    private String value02;
}


class EntityClassPK01 implements Serializable {
    private int value01;
    private String value02;

    // default constructor
    // equals + hashCode
}

@Entity // entityManager.find(EntityClass02.class, EntityClassPK02());
class EntityClass02{
    @EmbeddedId
    private EntityClassPK02 pk;
}

@Embeddable
class EntityClassPK02 implements Serializable {
    private int value01;
    private String value02;

    // default constructor
    // equals + hashCode
}


