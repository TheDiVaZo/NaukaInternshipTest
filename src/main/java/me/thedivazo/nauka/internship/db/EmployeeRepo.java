package me.thedivazo.nauka.internship.db;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
public interface EmployeeRepo {
    void createTable()  throws Exception;
    boolean addEmployee(EmployeeEntity employeeEntity) throws Exception;
    Optional<EmployeeEntity> findById(int id)  throws Exception;
    Iterable<EmployeeEntity> groupByName()  throws Exception;
    Iterable<EmployeeEntity> findBetween(LocalDate start, LocalDate end)  throws Exception;
}
