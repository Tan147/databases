package ru.itmo.databases.kurs04.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "tb_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "group")
    @JoinColumn(name = "group_id")
    private ArrayList<Climber> group = new ArrayList<Climber>();

    @OneToOne
    @Column(unique = true, nullable = false)
    private Mountain mountain;

    @Column(name = "full_name_of_the_head", unique = true, nullable = false)
    private String fullNameOfTheHead;

    @Column(name = "ascent_date", nullable = false)
    private LocalDate ascentDate;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @Column(nullable = false)
    private int cost;

    public Group(Climber climber, Mountain mountain, String fullNameOfTheHead, LocalDate ascentDate, int maxMembers, int cost) {
        this.mountain = mountain;
        this.fullNameOfTheHead = fullNameOfTheHead;
        this.ascentDate = ascentDate;
        this.maxMembers = maxMembers;
        this.cost = cost;
        for (int i = 0; i < group.size(); i++){
            if (group.size() < maxMembers && group.get(i) != climber && ascentDate.isAfter(LocalDate.now())) {
                group.add(climber);
            }
            break;
        }
    }

    public Group() {
    }

    public Group(Mountain mountain, String fullNameOfTheHead, LocalDate ascentDate, int maxMembers, int cost) {
        this.mountain = mountain;
        this.fullNameOfTheHead = fullNameOfTheHead;
        this.ascentDate = ascentDate;
        this.maxMembers = maxMembers;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mountain getMountain() {
        return mountain;
    }

    public void setMountain(Mountain mountain) {
        this.mountain = mountain;
    }

    public String getFullNameOfTheHead() {
        return fullNameOfTheHead;
    }

    public void setFullNameOfTheHead(String fullNameOfTheHead) {
        this.fullNameOfTheHead = fullNameOfTheHead;
    }

    public LocalDate getAscentDate() {
        return ascentDate;
    }

    public void setAscentDate(LocalDate ascentDate) {
        this.ascentDate = ascentDate;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxParticipants) {
        this.maxMembers = maxMembers;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public void add(Climber climber){
        for (int i = 0; i < group.size(); i++){
            if (group.size() < maxMembers && group.get(i) != climber && ascentDate.isAfter(LocalDate.now())){
                group.add(climber);
            }
            break;
        }
    }
}
