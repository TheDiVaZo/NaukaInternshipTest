package me.thedivazo.nauka.internship.command;

import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.db.EmployeeEntity;
import me.thedivazo.nauka.internship.service.EmployeeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public final class GetOfBirthdayBetweenDatesCommand implements Command {

    private final EmployeeService employeeService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Пожалуйста, введите начальный год (формат: гггг): ");

        LocalDate localDateStart;
        while (true) {
            try {
                int startDate = scanner.nextInt();
                if (startDate < 1900 || startDate > LocalDate.now().getYear()) {
                    System.out.println("Неверный год. Пожалуйста, введите год в диапазоне от 1900 до текущего года.");
                    continue;
                }
                localDateStart = LocalDate.of(startDate, 1, 1);
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат года. Пожалуйста, введите год в формате гггг.");
                scanner.next();
                continue;
            }
        }

        System.out.println("Пожалуйста, введите конечный год (формат: гггг): ");

        LocalDate localDateEnd;
        while (true) {
            try {
                int endDate = scanner.nextInt();
                if (endDate < localDateStart.getYear() || endDate > LocalDate.now().getYear()) {
                    System.out.println("Неверный год. Пожалуйста, введите год в диапазоне от "+localDateStart.getYear()+" до текущего года");
                    continue;
                }
                localDateEnd = LocalDate.of(endDate, 1, 1);
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат года. Пожалуйста, введите год в формате 'гггг'.");
                scanner.next();
                continue;
            }
        }

        List<EmployeeEntity> employees = employeeService.findBetween(localDateStart, localDateEnd);
        if (employees.isEmpty()) {
            System.out.println("Нет сотрудников в указанном диапазоне.");
            return;
        }

        System.out.println("Список сотрудников, день рождения который с "+localDateStart.getYear()+" года по "+localDateEnd.getYear()+" год:");
        for (EmployeeEntity employee : employees) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            System.out.printf("%s %s (%s)\n", employee.getName(), employee.getSurname(), employee.getBirthday().format(formatter));
        }
    }
}
