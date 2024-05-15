package ru.itmo.databases.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_students")
public class Student {
    @Id
    private long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
}
