package ru.milandr.courses.alfyorova;

import java.sql.*;
import java.util.*;

public class Application {
    private static ResultSet resultSet;
    private static Connection connection = null;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ArrayList<Integer> userId = new ArrayList<>();
    private static ArrayList<String> userSurnames = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users_database",
                    "alena", "password");
            if (connection == null) {
                System.out.println("Error: no connection with users_database");
                System.exit(-1);
            }
            statement = connection.createStatement();
            System.out.println("User with max id:");
            findUserWithMaxId();

            System.out.println("----------------------------");
            System.out.println("Users with address as Box 677, 8665 Ante Road:");
            findUsersWithAddress();

            System.out.println("----------------------------");
            System.out.println("Users in order:");
            printUsersInOrder();

            System.out.println("----------------------------");
            System.out.println("Average postal code:");
            countAveragePostalIndex();

            System.out.println("----------------------------");
            System.out.println("Empty addresses:");
            findAddressWithoutUsers();
            statement.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static void findUserWithMaxId() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.id = ?");
            while (resultSet.next()) {
                userId.add(resultSet.getInt("id"));
            }
            userId.sort(Collections.reverseOrder());
            preparedStatement.setInt(1, userId.get(0));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id = " + resultSet.getString("id") +
                        "; Name Surname: " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") + "; address_id: "
                        + resultSet.getString("address_id") + "; phone_number: "
                        + resultSet.getString("phone_number"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    private static void findUsersWithAddress() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM users JOIN addresses ON " +
                    "addresses.id = users.address_id"
                    + " WHERE addresses.address =  'P.O. Box 677, 8665 Ante Road'");
            while (resultSet.next()) {
                System.out.println("id = " + resultSet.getString("id") +
                        "; Name Surname: " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") + "; address_id: "
                        + resultSet.getString("address_id") + "; phone_number: "
                        + resultSet.getString("phone_number"));
            }
            resultSet.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    private static void printUsersInOrder() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.last_name = ?");
            while (resultSet.next()) {
                userSurnames.add(resultSet.getString("last_name"));
            }
            userSurnames.sort(String.CASE_INSENSITIVE_ORDER);

            for (String usersSurname : userSurnames) {
                preparedStatement.setString(1, usersSurname);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                System.out.println("surname: " + resultSet.getString("last_name")
                        + "; name: " + resultSet.getString("first_name")
                        + "; id = " + resultSet.getString("id")
                        + "; address_id: " + resultSet.getString("address_id")
                        + "; phone_number: " + resultSet.getString("phone_number"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    private static void countAveragePostalIndex() {
        try {
            double sum = 0;
            int quatity = 0;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM addresses");
            while (resultSet.next()) {
                try {
                    sum += (double) Integer.parseInt(resultSet.getString("postal_code"));
                    quatity++;
                } catch (NumberFormatException e) {
                }
            }
            System.out.printf("%.2f\n", sum / quatity);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    private static void findAddressWithoutUsers() {
        try {
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT addresses.id, addresses.address FROM addresses\n" +
                    "FULL OUTER JOIN users on addresses.id = users.address_id\n" +
                    "WHERE users.address_id is null\n");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("address"));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}