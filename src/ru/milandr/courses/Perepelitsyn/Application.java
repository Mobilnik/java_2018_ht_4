package ru.milandr.courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static ru.milandr.courses.LastNameArray.getLastNames;
import static ru.milandr.courses.MaxId.getMaxId;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/users_database",
                "postgres", "z1239879810");
             Statement stmt = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            System.out.println("\n---THE GREATEST ID---\n");
            ResultSet result = stmt.executeQuery("select * from users");
            int maxId = getMaxId(result);
            ResultSet maxIdPearson = stmt.executeQuery("select * from users ");
            while (maxIdPearson.next()) {
                if (maxIdPearson.getInt("id") == maxId) {
                    System.out.println("Person with the greatest Id is:");
                    System.out.println("Id: " + maxIdPearson.getString("id"));
                    System.out.println("First name : " + maxIdPearson.getString("first_name"));
                    System.out.println("Last name: " + maxIdPearson.getString("last_name"));
                    System.out.println("Address Id: " + maxIdPearson.getString("address_id"));
                    System.out.println("Phone number : " + maxIdPearson.getString("phone_number"));
                }
            }
            System.out.println("\n---LASTNAMES LIST---\n");
            ResultSet lastNameForArray = stmt.executeQuery("select * from users");
            ArrayList<String> lastNamesArray = getLastNames(lastNameForArray);
            Collections.sort(lastNamesArray);
            for (String arrayCounter : lastNamesArray) {
                ResultSet lastNameCounter = stmt.executeQuery("select * from users");
                while (lastNameCounter.next()) {
                    if ((lastNameCounter.getString("last_name").equals(arrayCounter))) {
                        System.out.println(lastNameCounter.getInt("id") + " " +
                                lastNameCounter.getString("last_name") + " " +
                                lastNameCounter.getString("first_name") + " " +
                                lastNameCounter.getString("phone_number"));
                    }
                }
            }

            System.out.println("\n---AVERAGE INDEX---\n");
            ResultSet index = stmt.executeQuery("select * from addresses");
            int indexSum = 0;
            int numberOfSum = 0;
            while (index.next()) {
                try {
                    indexSum += (double) Integer.parseInt(index.getString("postal_code"));
                    numberOfSum++;
                } catch (NumberFormatException e) {
                }
            }
            System.out.println("The average index is " + indexSum / numberOfSum);

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

