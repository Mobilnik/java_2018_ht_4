package ru.milandr.courses.rasskazov;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class Database {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            if (connection == null) {
                System.out.println("No connection to the database");
            } else System.out.println("Connection completed");
            statement = connection.createStatement();


            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM users");
            Integer maxID = 0;
            while (resultSet1.next()) {
                Integer currentID = resultSet1.getInt("id");
                maxID = Math.max(maxID, currentID);
            }
            System.out.println("Maximum ID is " + maxID);


            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM users JOIN addresses ON users.address_id = addresses.id WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'");
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("first_name") + " " + resultSet2.getString("last_name") + " " + resultSet2.getInt("address_id") + " " + resultSet2.getString("phone_number") + " " + resultSet2.getInt("id") + " lives at this very address");
            }


            ResultSet resultSet3 = statement.executeQuery("SELECT * FROM users");
            ArrayList<String> surnames = new ArrayList<>();
            while (resultSet3.next()) {
                String surname = resultSet3.getString("last_name");
                surnames.add(surname);
            }
            Collections.sort(surnames);
            for (String surname1 : surnames) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where last_name = ?");
                preparedStatement.setString(1, surname1);
                ResultSet resultSet31 = preparedStatement.executeQuery();
                while (resultSet31.next()) {
                    System.out.println(resultSet31.getString("first_name")+ " " + resultSet31.getString("last_name")+ " " + resultSet31.getInt("address_id") + " " + resultSet31.getString("phone_number")+ " " + resultSet31.getInt("id"));
                }
            }


            ResultSet resultSet4 = statement.executeQuery("SELECT * FROM addresses");
            Integer sum = 0;
            Integer count = 0;
            boolean isNumber = true;
            while (resultSet4.next()) {
                try {
                    sum = sum + Integer.parseInt(resultSet4.getString("postal_code"));
                    count = count + 1;
                } catch (NumberFormatException e) {
                    isNumber = false;
                }
            }
                System.out.println("The medium number of a postal address is " + sum / count);

            resultSet1.close();
            resultSet2.close();
            resultSet3.close();
            resultSet4.close();
            //resultSet5.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Unable to open the database");
            System.exit(0);
        }
    }
}
