package me.thedivazo.nauka.internship;

import lombok.AllArgsConstructor;
import me.thedivazo.nauka.internship.command.Command;
import me.thedivazo.nauka.internship.command.FindByIDCommand;
import me.thedivazo.nauka.internship.command.GetGroupedByNameCommand;
import me.thedivazo.nauka.internship.command.GetOfBirthdayBetweenDatesCommand;
import me.thedivazo.nauka.internship.db.EmployeeService;
import me.thedivazo.nauka.internship.util.EmployeeUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author TheDiVaZo
 * created on 22.10.2024
 */
@AllArgsConstructor
public class App {
    private final PrintStream out;
    private final InputStream in;
    
    private final EmployeeService employeeService;
    
    public final void start() {
        Scanner scanner = new Scanner(in);
        
        try {
            employeeService.initDatabase();
            for (int i = 0; i < 20; i++) {
                employeeService.addEmployee(EmployeeUtils.generateRandomEmployee());
            }
            out.println("Набор случайных сотрудников успешно добавлен");
        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("Ошибка при инициализации базы данных. Пожалуйста, проверьте конфигурацию на корректность данных");
            return;
        }
        Command findByIdCommand = new FindByIDCommand(employeeService, scanner, out);
        Command getEmployeesBetweenDateCommand = new GetOfBirthdayBetweenDatesCommand(employeeService, scanner, out);
        Command getGroupedByNameCommand = new GetGroupedByNameCommand(employeeService, scanner, out);
        out.println("Приветствуем вас в системе управления персоналом!");
        while (true) {
            out.println("Пожалуйста, выберите действие:");
            out.println("1 - получить пользователя по ID");
            out.println("2 - получить группированные имена сотрудников");
            out.println("3 - получить всех пользователей, даты рождения которых находятся в указанном диапазоне");
            out.println("4 - завершить программу");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                out.println("Пожалуйста, введите число.");
                continue;
            }
            if (choice < 1 || choice > 5) {
                out.println("Число должно быть от 1 до 5");
                continue;
            }
            try {
                switch (choice) {
                    case 1:
                        findByIdCommand.execute(); break;
                    case 2:
                        getGroupedByNameCommand.execute(); break;
                    case 3:
                        getEmployeesBetweenDateCommand.execute(); break;
                    case 4:
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace(out);
                out.println("" +
                        "Возникла ошибка при выполнении команды. " +
                        "Пожалуйста, проверьте корректноть базы данных и попробуйте еще раз." +
                        "");

            }
        }
    }
}
