package me.thedivazo.nauka.internship.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
public interface EmployeeService {
    void initDatabase();
    boolean addEmployee(EmployeeEntity employeeEntity);
    Optional<EmployeeEntity> findById(int id);
    List<String> groupByName();
    List<EmployeeEntity> findBetween(LocalDate start, LocalDate end);
}
