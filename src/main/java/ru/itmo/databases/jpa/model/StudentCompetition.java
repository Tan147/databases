package ru.itmo.databases.jpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students_competitions")
@IdClass(StudentCompetitionKey.class)
public class StudentCompetition {
    @Id
    @ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
    private int studentId;

    @Id
    @ManyToOne(targetEntity = Competition.class, fetch = FetchType.LAZY)
    private int competitionId;

    private PlaceNumber placeNumber;

    @OneToOne
    private Prize prize;
}
