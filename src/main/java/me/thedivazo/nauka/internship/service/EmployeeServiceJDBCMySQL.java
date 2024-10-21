package me.thedivazo.nauka.internship.service;

import me.thedivazo.nauka.internship.config.DBConfig;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.db.EmployeeRepoJDBCMySQL;
import me.thedivazo.nauka.internship.exception.DBException;
import me.thedivazo.nauka.internship.util.ListUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public final class EmployeeServiceJDBCMySQL implements EmployeeService {
    private final EmployeeRepoJDBCMySQL employeeRepo;

    public EmployeeServiceJDBCMySQL(DBConfig dbConfig) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.employeeRepo = new EmployeeRepoJDBCMySQL(dbConfig.host(), dbConfig.port(), dbConfig.database(), dbConfig.username(), dbConfig.password());
    }

    @Override
    public void initDatabase() {
        try {
            employeeRepo.createTable();
        } catch (Exception e) {
            throw new DBException("Error create table", e);
        }
    }

    @Override
    public boolean addEmployee(EmployeeEntity employeeEntity) {
        try {
            return employeeRepo.addEmployee(employeeEntity);
        } catch (Exception e) {
            throw new DBException("Error add employee."+employeeEntity,e);
        }
    }

    @Override
    public Optional<EmployeeEntity> findById(int id) {
        try {
            return employeeRepo.findById(id);
        } catch (Exception e) {
            throw new DBException("Error find for id",e);
        }
    }

    @Override
    public List<EmployeeEntity> groupByName() {
        try {
            return ListUtils.getListView(employeeRepo.groupByName());
        } catch (Exception e) {
            throw new DBException("Error get grouped employees",e);
        }
    }

    @Override
    public List<EmployeeEntity> findBetween(LocalDate start, LocalDate end) {
        try {
            return ListUtils.getListView(employeeRepo.findBetween(start, end));
        } catch (Exception e) {
            throw new DBException("Error find employees between "+start+" and "+end,e);
        }
    }
}
