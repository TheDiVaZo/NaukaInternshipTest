package me.thedivazo.nauka.internship.command;

import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.db.EmployeeService;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public final class FindByIDCommand implements Command {
    private final EmployeeService employeeService;
    private final Scanner scanner;
    private final PrintStream out;

    @Override
    public void execute() {
        out.println("Пожалуйста, введите ID сотрудника: ");
        int id;
        while (true) {
            try {
                id = scanner.nextInt();
                break;
            } catch (Exception e) {
                out.println("Идентификатор сотрудника должен быть числом. Пожалуйста, повторите ввод.");
            }
        }
        Optional<EmployeeEntity> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            out.println("Сотрудник не найден");
        } else {
            out.printf("""
                            ID: %s,
                            Имя: %s %s,
                            Возраст: %s,
                            Отдел: %s,
                            Зарплата: %sр.
                            """,
                    employee.get().getId(),
                    employee.get().getName(),
                    employee.get().getSurname(),
                    ChronoUnit.YEARS.between(employee.get().getBirthday(), LocalDate.now()),
                    employee.get().getDepartment(),
                    employee.get().getSalaryInKopeck() / 100d);
        }
    }
}
