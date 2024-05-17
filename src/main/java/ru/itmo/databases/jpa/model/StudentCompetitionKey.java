package ru.itmo.databases.jpa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentCompetitionKey implements Serializable {
    private int studentId;
    private int competitionId;
}
