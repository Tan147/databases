package ru.itmo.databases.jpa;

import jakarta.persistence.*;

public class Prize {
    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int place;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

}
