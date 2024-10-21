package me.thedivazo.nauka.internship.command;


import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.service.EmployeeService;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public final class GetGroupedByNameCommand implements Command {
    private final EmployeeService employeeService;
    private final Scanner scanner;

    @Override
    public void execute() {
        List<EmployeeEntity> employees = employeeService.groupByName();
        if (employees.isEmpty()) {
            System.out.println("В базе данных нет сотрудников");
        }
        for (EmployeeEntity employee : employees) {
            System.out.printf("id: %d, имя: %s %s\n", employee.getId(), employee.getName(), employee.getSurname());
        }
    }
}
