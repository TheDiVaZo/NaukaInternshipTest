package me.thedivazo.nauka.internship.db;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
public interface EmployeeRepo {
    void createTable();
    boolean addEmployee(EmployeeEntity employeeEntity);
    Optional<EmployeeEntity> findById(int id);
    Iterable<EmployeeEntity> groupByName(String name);
    Iterable<EmployeeEntity> findBetween(LocalDate start, LocalDate end);
}
