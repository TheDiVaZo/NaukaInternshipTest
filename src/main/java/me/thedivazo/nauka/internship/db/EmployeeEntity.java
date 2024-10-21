package me.thedivazo.nauka.internship.db;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
@Getter
@ToString
public class EmployeeEntity {
    private final int id;
    private final String name;
    private final String surname;
    private final java.time.LocalDate birthday;
    private final String department;
    private final int salaryInKopeck;

    public EmployeeEntity(String name, String surname, LocalDate birthday, String department, int salaryInKopeck) {
        this.id = -1;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.department = department;
        this.salaryInKopeck = salaryInKopeck;
    }

    EmployeeEntity(int id, String name, String surname, LocalDate birthday, String department, int salaryInKopeck) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.department = department;
        this.salaryInKopeck = salaryInKopeck;
    }
}
