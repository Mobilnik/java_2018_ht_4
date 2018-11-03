package ru.milandr.courses.vorobyev;

import java.sql.*;
import java.util.*;


public class Main {
    private static boolean isNumber(String string) {
        if (string == null || string.length() == 0) return false;

        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() == 1) {
                return false;
            }
            i = 1;
        }

        char c;
        for (; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {

        List<Integer> usersIdentificators = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<Integer> addressesList = new ArrayList<>();
        List<Integer> extraAddresses = new ArrayList<>();
        Set<Integer> userAddresses = new HashSet<>();

        int size;
        double result;

        ResultSet rs;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/users_database",
                "daniilvorobyev", "postgres");
             Statement stmt = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            //task1
            rs = stmt.executeQuery("SELECT id from users");
            while (rs.next())
                usersIdentificators.add(rs.getInt("id"));
            Collections.reverse(usersIdentificators);
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM users where id = ?");
            pstmt.setInt(1, usersIdentificators.get(0));
            rs = pstmt.executeQuery();
            rs.next();
            System.out.println("User with the biggest id:\n"
                    + rs.getString("first_name")
                    + " " + rs.getString("last_name")
                    + "(id = " + rs.getString("id")
                    + ", adress_id = " + rs.getString("address_id")
                    + ", phone number = " + rs.getString("phone_number")
                    + ")\n");

            //task2
            rs = stmt.executeQuery("SELECT first_name, last_name, users.id, phone_number " +
                    "FROM users  INNER JOIN addresses " +
                    "ON users.address_id = addresses.id " +
                    "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'");
            System.out.println("Users from address = P.O. Box 677, 8665 Ante Road:");
            while (rs.next())
                System.out.println(rs.getString("first_name")
                        + " " + rs.getString("last_name")
                        + "(id = " + rs.getString("id")
                        + ", phone number = " + rs.getString("phone_number")
                        + ")");

            //task3
            rs = stmt.executeQuery("SELECT last_name FROM users ");
            while (rs.next())
                lastNames.add(rs.getString("last_name"));
            Collections.sort(lastNames);
            pstmt = conn.prepareStatement(" SELECT * FROM users where last_name = ?");
            size = lastNames.size();
            for (int i = 0; i < size; i++) {
                if (i == 0)
                    System.out.println("\nAll users in alphabet order:");
                pstmt.setString(1, lastNames.get(i));
                rs = pstmt.executeQuery();
                rs.next();
                System.out.println(rs.getString("first_name")
                        + " " + rs.getString("last_name")
                        + "(id = " + rs.getString("id")
                        + ", adress_id = " + rs.getString("address_id")
                        + ", phone number = " + rs.getString("phone_number")
                        + ")");
            }

            //task4
            rs = stmt.executeQuery("SELECT postal_code FROM addresses");
            while (rs.next()) {
                if (isNumber(rs.getString("postal_code")))
                    addressesList.add(rs.getInt("postal_code"));
            }
            size = addressesList.size();
            Optional sum = addressesList.stream().reduce((a, b) -> a + b);

            if (sum.isPresent()) {
                result = (double)sum.get();
                System.out.printf("\nAverage postal code:\n%.3f\n", result / size);
            }
            else
                System.out.println("ERROR with summation of postal codes");

            //task5
            rs = stmt.executeQuery("SELECT addresses.id FROM addresses " +
                    "RIGHT JOIN users " +
                    "ON users.address_id = addresses.id");
            while (rs.next())
                userAddresses.add(rs.getInt("id"));
            rs = stmt.executeQuery("SELECT id FROM addresses ");
            while (rs.next()) {
                if (!userAddresses.contains(rs.getInt("id")))
                    extraAddresses.add(rs.getInt("id"));
            }
            size = extraAddresses.size();
            pstmt = conn.prepareStatement(" SELECT * FROM addresses where id = ?");
            for (int i = 0; i < size; i++) {
                if (i == 0)
                    System.out.println("\nExtra addresses:");
                pstmt.setInt(1, extraAddresses.get(i));
                rs = pstmt.executeQuery();
                rs.next();
                System.out.println(rs.getString("address")
                        + "(id = " + rs.getString("id")
                        + ", city = " + rs.getString("city")
                        + ", postal code = " + rs.getString("postal_code")
                        + ")");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

