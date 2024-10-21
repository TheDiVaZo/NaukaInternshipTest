package me.thedivazo.nauka.internship.util;

import me.thedivazo.nauka.internship.db.EmployeeEntity;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public final class EmployeeUtils {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final String[] NAMES = {"John", "Jane", "Bob", "Alice", "Eve" };
    private static final String[] SURNAMES = {"Doe", "Smith", "Johnson", "Williams", "Davis" };
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;
    private static final int MIN_SALARY = 50000;
    private static final int MAX_SALARY = 200000;
    private static final String[] DEPARTMENTS = {"HR", "IT", "Finance", "Marketing", "Sales" };

    private static String randomArrayString(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    private static int randomNumber(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private static LocalDate randomBirthday() {
        LocalDate currentDate = LocalDate.now();
        return LocalDate.of(
                currentDate.getYear() - randomNumber(MIN_AGE, MAX_AGE),
                randomNumber(1, 12),
                randomNumber(1, 30)
        );
    }

    public static EmployeeEntity generateRandomEmployee() {
        return new EmployeeEntity(
                randomArrayString(NAMES),
                randomArrayString(SURNAMES),
                randomBirthday(),
                randomArrayString(DEPARTMENTS),
                randomNumber(MIN_SALARY, MAX_SALARY)
        );
    }
}
