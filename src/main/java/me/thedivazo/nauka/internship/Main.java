package me.thedivazo.nauka.internship;

import me.thedivazo.nauka.internship.config.DBConfig;
import me.thedivazo.nauka.internship.config.EnvConfig;
import me.thedivazo.nauka.internship.db.EmployeeService;
import me.thedivazo.nauka.internship.db.EmployeeServiceJDBCMySQL;
import me.thedivazo.nauka.internship.db.EmployeeServiceLocal;


public class Main {

    public static void main(String[] args) {
        DBConfig dbConfig;
        EmployeeService employeeService;
        try {
            dbConfig = new EnvConfig();
            if (dbConfig.getBaseType() == DBConfig.BaseType.MYSQL) {
                employeeService = new EmployeeServiceJDBCMySQL(dbConfig);
                System.out.println("База данных MySQL установлена");
            } else
                employeeService = new EmployeeServiceLocal();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось загрузить данные из конфига. Пожалуйста, проверьте конфигурацию");
            return;
        }
        App app = new App(System.out, System.in, employeeService);
        app.start();
    }
}