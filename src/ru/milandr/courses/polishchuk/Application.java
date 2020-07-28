package ru.milandr.courses.polishchuk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

class SQLDemo {
    private Connection connection;

    public SQLDemo(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    private void printUser(ResultSet user) throws SQLException {
        System.out.println("id: " + user.getBigDecimal("id") + '\n' +
                "first_name: " + user.getString("first_name") + '\n' +
                "last_name: " + user.getString("last_name") + '\n' +
                "phone_number: " + user.getString("phone_number"));
    }

    private void printUsers(ResultSet users) throws SQLException {
        while (users.next()) {
            printUser(users);
        }
    }

    private void printUserWithIdJava(BigDecimal id) {
        try (PreparedStatement stmtSelectUsersIds = connection.prepareStatement("select * from users where id = ?")) {
            stmtSelectUsersIds.setBigDecimal(1, id);

            ResultSet user = stmtSelectUsersIds.executeQuery();
            if (!user.next())
                throw new SQLException("Zero users found!");
            if (!user.isLast())
                throw new SQLException("Duplicate user id found!");
            printUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printUserWithMaxIdJava() {
        try (PreparedStatement stmtSelectUsersIds = connection.prepareStatement("select * from users")) {
            BigDecimal maxId = new BigDecimal(-1);
            ResultSet userIds = stmtSelectUsersIds.executeQuery();

            while (userIds.next()) {
                BigDecimal id = userIds.getBigDecimal("id");
                if (maxId.compareTo(id) < 0) {
                    maxId = new BigDecimal(String.valueOf(id));
                }
            }
            printUserWithIdJava(maxId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try (Connection dbConn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/users_database",
                "postgres", "postgres")) {
            SQLDemo demo = new SQLDemo(dbConn);

            demo.printUserWithMaxIdJava();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}