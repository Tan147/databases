package ru.itmo.databases.kurs04.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.itmo.databases.kurs04.model.Climber;

import java.util.ArrayList;

@Data
@Entity
@Table(name = "tb_reserve")
public class Reserve {
    @Id
    private int id;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @OneToMany
    @JoinColumn(name = "reserve_id")
    private ArrayList<Climber> reserve = new ArrayList<Climber>();

    public Reserve(int id, int maxMembers) {
        this.id = id;
        this.maxMembers = maxMembers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public void addInReserve(Climber climber){
        for (int i = 0; i < reserve.size(); i++){
            if (reserve.size() < maxMembers && reserve.get(i) != climber) reserve.add(climber);
            break;
        }
    }
}
