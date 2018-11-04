package ru.milandr.courses.bondarev;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class JavaDBWorks {

    private static Integer getMaxID(ResultSet rs) throws SQLException {
        Integer maxID = -1;

        try {
            while (rs.next()) {
                Integer tmpID = rs.getInt("id");
                maxID = Math.max(maxID, tmpID);
            }
        } catch (SQLException e) {
            throw e;
        }

        return maxID;
    }

    private static int getAveragePC(ResultSet rs) throws SQLException {
        double sum = 0;
        int counter = 0;

        try {
            while (rs.next()) {
                try {
                    sum += (double) Integer.parseInt(rs.getString("postal_code"));
                    counter++;
                } catch (NumberFormatException e) { }
            }
        } catch (SQLException e) { throw e; }

        if (counter == 0)
            return 0;

        return (int) Math.round (sum / counter);
    }

    private static ArrayList<String> getSurnames(ResultSet rs) throws SQLException {
        ArrayList<String> surnames = new ArrayList<>();

        try {
            while (rs.next()) {
                surnames.add(rs.getString("last_name"));
            }
        } catch (SQLException e) {
            throw e;
        }

        return surnames;
    }

    public static void main(String[] args) throws SQLException {

        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String DB_Driver = "org.postgresql.Driver";

        Connection conn = null;

        try {
            Class.forName(DB_Driver);

            conn = DriverManager.getConnection(DB_URL,
                    "postgres", "postgres");

            if (conn == null) {
                System.out.println("No connection with DB!");
                System.exit(0);
            }
            System.out.println("DB has been connected successfully!");


            //First task//
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            Integer maxID = getMaxID(rs);

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, maxID);
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("\nUser with maximum ID = " + maxID +
                        "\nname: " + rs.getString("first_name") +
                        "\nsurname: " + rs.getString("last_name") +
                        "\naddress id: " + rs.getInt("address_id") +
                        "\nphone number: " + rs.getString("phone_number"));
            }

            //Second task//
            rs = stmt.executeQuery("SELECT * FROM users JOIN addresses " +
                    "ON users.address_id = addresses.id " +
                    "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'");
            System.out.println("\nThey live in P.O. Box 677, 8665 Ante Road :");
            while (rs.next()){
                System.out.println(rs.getInt("id") + " " +
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") + " " +
                        rs.getString("phone_number"));
            }


            //Third task//
            rs = stmt.executeQuery("SELECT * FROM users");
            ArrayList<String> surname = getSurnames(rs);
            Collections.sort(surname);

            System.out.println("\nSorted by surname users:");

            for (String srnm : surname) {
                ps = conn.prepareStatement("SELECT * FROM users WHERE last_name = ?");
                ps.setString(1, srnm);
                rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " " +
                            rs.getString("last_name") + " " +
                            rs.getString("first_name") + " " +
                            rs.getString("phone_number"));
                }
            }

            //Foouth task//
            rs = stmt.executeQuery("SELECT * FROM addresses");
            System.out.println("\nAverage postal code is " + getAveragePC(rs));

            //Fifth task//

            //not done//

            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }



    }
}