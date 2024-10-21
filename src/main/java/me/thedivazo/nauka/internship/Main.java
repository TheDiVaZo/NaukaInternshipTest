package me.thedivazo.nauka.internship;

import me.thedivazo.nauka.internship.command.Command;
import me.thedivazo.nauka.internship.command.FindByIDCommand;
import me.thedivazo.nauka.internship.command.GetOfBirthdayBetweenDatesCommand;
import me.thedivazo.nauka.internship.command.GetGroupedByNameCommand;
import me.thedivazo.nauka.internship.config.DBConfig;
import me.thedivazo.nauka.internship.config.DBConfigHocon;
import me.thedivazo.nauka.internship.service.EmployeeService;
import me.thedivazo.nauka.internship.service.EmployeeServiceJDBCMySQL;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException {
        DBConfig dbConfig;
        try {
            dbConfig = new DBConfigHocon("db");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Введите корректную конфигурацию и запустите приложение заново.");
            return;
        }
        EmployeeService employeeService;
        try {
            employeeService = new EmployeeServiceJDBCMySQL(dbConfig);
            employeeService.initDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Не удалось найти класс драйвера. Обратитесь к разработчику.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при инициализации базы данных. Пожалуйста, проверьте конфигурацию на корректность данных");
            return;
        }
        Command findByIdCommand = new FindByIDCommand(employeeService, scanner);
        Command getEmployeesBetweenDateCommand = new GetOfBirthdayBetweenDatesCommand(employeeService, scanner);
        Command getGroupedByNameCommand = new GetGroupedByNameCommand(employeeService, scanner);
        System.out.println("Приветствуем вас в системе управления персоналом!");
        while (true) {
            System.out.println("Пожалуйста, выберите действие:");
            System.out.println("1 - добавить пользователя");
            System.out.println("2 - получить пользователя по ID");
            System.out.println("3 - получить всех пользователей, группированных по имени");
            System.out.println("4 - получить всех пользователей, даты рождения которых находятся в указанном диапазоне");
            System.out.println("5 - завершить программу");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Пожалуйста, введите число.");
                continue;
            }
            if (choice < 1 || choice > 5) {
                System.out.println("Число должно быть от 1 до 5");
                continue;
            }
            try {
                switch (choice) {
                    case 1:
                        findByIdCommand.execute();
                    case 2:
                        getEmployeesBetweenDateCommand.execute();
                    case 3:
                        getGroupedByNameCommand.execute();
                    case 4:
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("" +
                        "Возникла ошибка при выполнении команды. " +
                        "Пожалуйста, проверьте корректноть базы данных и попробуйте еще раз." +
                        "");

            }
        }

    }
}