package me.thedivazo.nauka.internship.db;

import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 19.10.2024
 */
@AllArgsConstructor
public final class EmployeeRepoJDBCMySQL implements EmployeeRepo {
    private final String host;
    private final String port;
    private final String database;

    private final String username;
    private final String password;

    private String getURL() {
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                host,
                port,
                database);
    }

    private Connection getConnection() throws SQLException {
        boolean usernameIsEmpty = username == null || username.isEmpty() || username.isBlank();
        boolean passwordIsEmpty = password == null || password.isEmpty() || password.isBlank();
        try {
            return usernameIsEmpty || passwordIsEmpty ? DriverManager.getConnection(getURL()) : DriverManager.getConnection(getURL(), username, password);
        } catch (Exception e) {
            throw new SQLException("Error connecting to database. URL - "+getURL() + ", username: "+username, e);
        }
    }

    private void closeAutocloseable(AutoCloseable closeable) throws Exception {
        if (closeable != null)
            closeable.close();
    }

    private void noAutoCommitConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.setAutoCommit(false);
    }

    private void commitConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    private void rollbackConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.rollback();
    }

    private Iterable<EmployeeEntity> toEmployees(ResultSet resultSet) throws SQLException {
        List<EmployeeEntity> employees = new LinkedList<>();
        while (resultSet.next()) {
            employees.add(toEmployee(resultSet));
        }
        return employees;
    }

    private EmployeeEntity toEmployee(ResultSet resultSet) throws SQLException {
        return new EmployeeEntity(
                resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getString("SURNAME"),
                resultSet.getDate("BIRTHDAY").toLocalDate(),
                resultSet.getString("DEPARTMENT"),
                resultSet.getInt("SALARY_IN_KOPECK")
        );
    }

    @Override
    public void createTable() throws Exception {
        String query = """
                CREATE TABLE IF NOT EXISTS EMPLOYEE (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    NAME VARCHAR(255) NOT NULL,
                    SURNAME VARCHAR(255) NOT NULL,
                    BIRTHDAY DATE NOT NULL,
                    DEPARTMENT VARCHAR(255) NOT NULL,
                    SALARY_IN_KOPECK INT NOT NULL
                );
                """;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Failed create table", e);
        } finally {
            closeAutocloseable(connection);
            closeAutocloseable(statement);
        }
    }

    @Override
    public boolean addEmployee(EmployeeEntity employeeEntity) throws Exception {
        String query = """
                INSERT INTO EMPLOYEE (NAME, SURNAME, BIRTHDAY, DEPARTMENT, SALARY_IN_KOPECK)
                VALUES(?, ?, ?, ?, ?);
                """;

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            noAutoCommitConnection(connection);
            statement = connection.prepareStatement(query);
            statement.setString(1, employeeEntity.getName());
            statement.setString(2, employeeEntity.getSurname());
            statement.setDate(3, Date.valueOf(employeeEntity.getBirthday()));
            statement.setString(4, employeeEntity.getDepartment());
            statement.setInt(5, employeeEntity.getSalaryInKopeck());

            boolean result = statement.executeUpdate() != 0;
            commitConnection(connection);
            return result;
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw e;
        } finally {
            closeAutocloseable(connection);
            closeAutocloseable(statement);
        }
    }

    @Override
    public Optional<EmployeeEntity> findById(int id) throws Exception {
        String query = """
                SELECT ID, NAME, SURNAME, BIRTHDAY, DEPARTMENT, SALARY_IN_KOPECK 
                FROM EMPLOYEE 
                WHERE ID = ?;
                """;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(toEmployee(resultSet));
            } else {
                return Optional.empty();
            }
        } finally {
            closeAutocloseable(connection);
            closeAutocloseable(statement);
            closeAutocloseable(resultSet);
        }
    }

    @Override
    public Iterable<String> groupByName() throws Exception {
        String query = """
                SELECT NAME 
                FROM EMPLOYEE 
                GROUP BY NAME
                """;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<String> names = new LinkedList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString("NAME"));
            }
            return names;
        } finally {
            closeAutocloseable(connection);
            closeAutocloseable(statement);
            closeAutocloseable(resultSet);
        }
    }

    @Override
    public Iterable<EmployeeEntity> findBetween(LocalDate start, LocalDate end) throws Exception {
        String query = """
                SELECT ID, NAME, SURNAME, BIRTHDAY, DEPARTMENT, SALARY_IN_KOPECK 
                FROM EMPLOYEE 
                WHERE BIRTHDAY BETWEEN ? AND ?;
                """;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            resultSet = statement.executeQuery();

            return toEmployees(resultSet);
        } finally {
            closeAutocloseable(connection);
            closeAutocloseable(statement);
            closeAutocloseable(resultSet);
        }
    }
}
