package me.thedivazo.nauka.internship.command;

import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.service.EmployeeService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public final class FindByIDCommand implements Command {
    private final EmployeeService employeeService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Пожалуйста, введите ID сотрудника: ");
        int id;
        while (true) {
            try {
                id = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Идентификатор сотрудника должен быть числом. Пожалуйста, повторите ввод.");
            }
        }
        Optional<EmployeeEntity> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            System.out.println("Сотрудник не найден");
        } else {
            System.out.printf("""
                            ID: %s,
                            Имя: %s %s,
                            Возраст: %s,
                            Отдел: %s,
                            Зарплата: %sр.
                            """,
                    employee.get().getId(),
                    employee.get().getName(),
                    employee.get().getSurname(),
                    ChronoUnit.YEARS.between(LocalDate.now(), employee.get().getBirthday()),
                    employee.get().getDepartment(),
                    employee.get().getSalaryInKopeck() / 100d);
        }
    }
}
