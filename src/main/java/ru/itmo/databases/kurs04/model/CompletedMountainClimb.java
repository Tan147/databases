package ru.itmo.databases.kurs04.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_completed_mountain_climb")
public class CompletedMountainClimb {
    @Id
    private int id;

    @Column(nullable = false, updatable = false)
    private LocalDate start;

    @Column(nullable = false, updatable = false)
    private LocalDate finish;

    @Column(name = "number_of_members", nullable = false)
    private int numberOfMembers;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;

    public CompletedMountainClimb(int id, LocalDate start, LocalDate finish, int numberOfMembers, Group group,
                                  Mountain mountain) {
        this.id = id;
        this.start = start;
        this.finish = finish;
        this.numberOfMembers = numberOfMembers;
        this.group = group;
        this.mountain = mountain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Mountain getMountain() {
        return mountain;
    }

    public void setMountain(Mountain mountain) {
        this.mountain = mountain;
    }
}
