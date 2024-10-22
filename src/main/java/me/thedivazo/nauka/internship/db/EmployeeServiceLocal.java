package me.thedivazo.nauka.internship.db;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author TheDiVaZo
 * created on 22.10.2024
 */
public final class EmployeeServiceLocal implements EmployeeService {
    private final Int2ObjectMap<EmployeeEntity> database = new Int2ObjectOpenHashMap<>();
    private int lastId = 0;


    @Override
    public void initDatabase() {
        //Пустой, потому что база данных всегда инициализирована
    }

    @Override
    public boolean addEmployee(EmployeeEntity employeeEntity) {
        if(!database.containsValue(employeeEntity)) {
            database.put(lastId++, employeeEntity);
            return true;
        }
        else
            return false;
    }

    private EmployeeEntity getFromDatabase(int id, EmployeeEntity employeeEntity) {
        return new EmployeeEntity(
                id,
                employeeEntity.getName(),
                employeeEntity.getSurname(),
                employeeEntity.getBirthday(),
                employeeEntity.getDepartment(),
                employeeEntity.getSalaryInKopeck()
        );
    }

    @Override
    public Optional<EmployeeEntity> findById(int id) {
        return Optional.ofNullable(database.get(id)).map(employeeEntity -> getFromDatabase(id, employeeEntity));
    }

    private final List<EmployeeEntity> comparing(Comparator<? super Int2ObjectMap.Entry<EmployeeEntity>> employeeComparator) {
        return database.int2ObjectEntrySet().stream().sorted(employeeComparator).map(entry->getFromDatabase(entry.getIntKey(), entry.getValue())).toList();
    }

    private final List<EmployeeEntity> filtering(Predicate<? super EmployeeEntity> employeeComparator) {
        return database.int2ObjectEntrySet().stream().map(entry->getFromDatabase(entry.getIntKey(), entry.getValue())).filter(employeeComparator).toList();
    }

    @Override
    public List<String> groupByName() {
        Set<String> groupedNames = new HashSet<String>();
        for (EmployeeEntity value : database.values()) {
            groupedNames.add(value.getName());
        }
        return groupedNames.stream().toList();
    }

    @Override
    public List<EmployeeEntity> findBetween(LocalDate start, LocalDate end) {
        return filtering(employee->
                        (employee.getBirthday().equals(start) || employee.getBirthday().isAfter(start))
                        && (employee.getBirthday().equals(end) || employee.getBirthday().isBefore(end))
                );
    }
}
