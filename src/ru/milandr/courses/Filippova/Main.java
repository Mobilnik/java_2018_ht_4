package ru.milandr.courses.Filippova;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static boolean isNumeric(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws SQLException {

        ArrayList<Integer> identification = new ArrayList<>();
        ArrayList<String> surname = new ArrayList<>();
        ArrayList<Integer> postalCodes = new ArrayList<>();
        Set<Integer> addressId = new HashSet<>();

        int addrId = 0;
        double sum = 0;
        int i = 0;

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users_database",
                    "username", "password");

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }
            Statement stmt = conn.createStatement();


            //////first task//////

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM users ");
            while (resultSet.next()) {
                identification.add(resultSet.getInt("id"));
            }
            Collections.sort(identification, Collections.reverseOrder());

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE users.id = ?");
            preparedStatement.setInt(1, identification.get(0));
            resultSet = preparedStatement.executeQuery();
            System.out.println("The owner of the biggest id is: ");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name") + ": id =" + " " + resultSet.getString("id")
                        + "; phone number is" + " "
                        + resultSet.getString("phone_number")
                        + "; address id is " + resultSet.getString("address_id"));
            }


            //////second task////////

            resultSet = stmt.executeQuery("SELECT * FROM users JOIN addresses ON " +
                    " users.address_id = addresses.id " +
                    "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'");
            System.out.println("Residents of P.O. Box 677, 8665 Ante Road are :");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name") + ": id = " + resultSet.getString("id") +
                        "; phone number is " + resultSet.getString("phone_number"));
            }

            //////third task////////


            resultSet = stmt.executeQuery("SELECT last_name FROM users");
            while (resultSet.next()) {
                surname.add(resultSet.getString("last_name"));
            }
            Collections.sort(surname);
            preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE users.last_name = ?");
            System.out.println("Users In Alphabet Order: ");
            for (i = 0; i < surname.size(); i++) {
                preparedStatement.setString(1, surname.get(i));
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                System.out.println(resultSet.getString("last_name") + " "
                        + resultSet.getString("first_name") + ": id is "
                        + resultSet.getString("id")
                        + "; phone number is " + resultSet.getString("phone_number") +
                        "; address id is " + resultSet.getString("address_id"));

            }

            while (resultSet.next()) {
                System.out.println(resultSet.getString("last_name"));
            }

            ////////fourth task///////
            i = 0;
            resultSet = stmt.executeQuery("SELECT postal_code FROM addresses");
            while (resultSet.next()) {
                if (isNumeric(resultSet.getString("postal_code"))) {
                    postalCodes.add(resultSet.getInt("postal_code"));
                    sum = sum + postalCodes.get(i);
                    i++;

                }
            }

            System.out.println("Average Postal Code is " + sum / i);

            //////fifth task/////////

            resultSet = stmt.executeQuery("SELECT addresses.id FROM addresses INNER JOIN users" +
                    " ON addresses.id = users.address_id ");
            while (resultSet.next()) {
                addressId.add(resultSet.getInt("id"));
            }
            resultSet = stmt.executeQuery("SELECT id FROM addresses");
            while (resultSet.next()) {
                if (!addressId.contains(resultSet.getInt("id"))) {
                    addrId = resultSet.getInt("id");
                }
            }

            preparedStatement = conn.prepareStatement("SELECT * FROM addresses WHERE id = ?");
            preparedStatement.setInt(1, addrId);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Address where no one lives: ");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("address") + "; postal code is " +
                        resultSet.getString("postal_code") + "; address id is " +
                        resultSet.getString("id"));
            }

            /////////////////////////////////////////////

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}

