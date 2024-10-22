package me.thedivazo.nauka.internship.command;


import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.db.EmployeeService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public final class GetGroupedByNameCommand implements Command {
    private final EmployeeService employeeService;
    private final Scanner scanner;
    private final PrintStream out;

    @Override
    public void execute() {
        List<String> names = employeeService.groupByName();
        if (names.isEmpty()) {
            out.println("В базе данных нет сотрудников");
        }
        out.println("Группированные имена сотрудников:");
        for (String name : names) {
            out.println(name);
        }
    }
}
