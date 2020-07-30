package ru.milandr.courses.polishchuk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TablesEqualityMain {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (
                Connection conn1 = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/users_database",
                        "postgres", "postgres"
                );
                Connection conn2 = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/users_database",
                        "postgres", "postgres"
                )
        ) {
            TablesEqualityTester tester = new TablesEqualityTester(
                    conn1, conn2,
                    "users4test", "users4test1",
                    "id,first_name,last_name"
            );
            if (tester.equalityTest()) {
                System.out.println("Equals");
            } else {
                System.out.println("Not equal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}