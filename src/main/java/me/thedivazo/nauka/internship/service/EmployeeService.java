package me.thedivazo.nauka.internship.service;

import me.thedivazo.nauka.internship.db.EmployeeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
public interface EmployeeService {
    void createTable();
    boolean addEmployee(EmployeeEntity employeeEntity);
    Optional<EmployeeEntity> findById(int id);
    List<EmployeeEntity> groupByName(String name);
    List<EmployeeEntity> findBetween(LocalDate start, LocalDate end);
}
