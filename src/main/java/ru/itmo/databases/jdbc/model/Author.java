package ru.itmo.databases.jdbc.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Author {
    private int id;
    private String uniqueName;
    private LocalDate registeredAt;
    private boolean isActive = true;

}
