package ru.milandr.courses.kuznetsov;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Application {

    public static void main(String[] args) throws SQLException {

        ResultSet rs;

        ArrayList<String> lastNames = new ArrayList<>();
        ArrayList<Integer> postCodes = new ArrayList<>();
        ArrayList<Integer> userAddresses = new ArrayList<>();
        ArrayList<Integer> uselessAddresses = new ArrayList<>();
        int maxId = 0;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users_database",
                    "postgres", "postgres");

            Statement stmt = conn.createStatement();

            //1
            rs = stmt.executeQuery("SELECT id from users");
            while (rs.next()) {
                if (rs.getInt("id") > maxId)
                    maxId = rs.getInt("id");
            }
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM users where id = ?");
            pstmt.setInt(1, maxId);
            rs = pstmt.executeQuery();
            rs.next();
            System.out.println("User with the biggest id:\n"
                    + rs.getString("first_name")
                    + " " + rs.getString("last_name")
                    + " {id = " + rs.getString("id")
                    + ", adress_id = " + rs.getString("address_id")
                    + ", phone number = " + rs.getString("phone_number")
                    + "}\n");

            //2
            rs = stmt.executeQuery("SELECT * FROM users INNER JOIN addresses "
                    + "ON users.address_id = addresses.id "
                    + "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'");
            System.out.println("Users from 'P.O. Box 677, 8665 Ante Road':");
            while (rs.next()) {
                System.out.println(rs.getString("first_name")
                        + " " + rs.getString("last_name")
                        + " {id = " + rs.getString("id")
                        + ", adress_id = " + rs.getString("address_id")
                        + ", phone number = " + rs.getString("phone_number")
                        + "}");
            }

            //3
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next())
                lastNames.add(rs.getString("last_name"));
            Collections.sort(lastNames);
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE last_name = ?");
            for (int i = 0; i < lastNames.size(); i++) {
                if (i == 0)
                    System.out.println("\nUsers in alphabetic order:");
                pstmt.setString(1, lastNames.get(i));
                rs = pstmt.executeQuery();
                rs.next();
                System.out.println(rs.getString("first_name")
                        + " " + rs.getString("last_name")
                        + " {id = " + rs.getString("id")
                        + ", adress_id = " + rs.getString("address_id")
                        + ", phone number = " + rs.getString("phone_number")
                        + "}");
            }

            //4
            rs = stmt.executeQuery("SELECT postal_code FROM addresses");
            while (rs.next())
                if (isInt(rs.getString("postal_code")))
                    postCodes.add(rs.getInt("postal_code"));
            Optional sum = postCodes.stream().reduce((a, b) -> a + b);
            int midRes = (int) sum.get() / postCodes.size();
            double avg = (double) midRes;
            System.out.printf("\nAverage postal code is %.1f", avg);

            //5
            rs = stmt.executeQuery("SELECT addresses.id FROM addresses"
                    + " RIGHT JOIN users"
                    + " ON users.address_id = addresses.id");
            while (rs.next())
                userAddresses.add(rs.getInt("id"));
            rs = stmt.executeQuery("SELECT id FROM addresses");
            while (rs.next())
                if (!userAddresses.contains(rs.getInt("id")))
                    uselessAddresses.add(rs.getInt("id"));
            pstmt = conn.prepareStatement(" SELECT * FROM addresses where id = ?");
            if (uselessAddresses.size() == 0)
                System.out.println("\nNo empty adresses");
            else
                for (int i = 0; i < uselessAddresses.size(); i++) {
                    if (i == 0)
                        System.out.println("\nExtra addresses:");
                    pstmt.setInt(1, uselessAddresses.get(i));
                    rs = pstmt.executeQuery();
                    rs.next();
                    System.out.println(rs.getString("address")
                            + " {id = " + rs.getString("id")
                            + ", city = " + rs.getString("city")
                            + ", postal code = " + rs.getString("postal_code")
                            + "}");
                }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static boolean isInt(String postalCode) {
        int counter = 0;
        for (int i = 0; i < postalCode.length(); i++) {
            if (postalCode.charAt(i) > '0' && postalCode.charAt(i) < '9')
                counter++;
        }
        if (counter == postalCode.length())
            return true;
        else
            return false;
    }
}




