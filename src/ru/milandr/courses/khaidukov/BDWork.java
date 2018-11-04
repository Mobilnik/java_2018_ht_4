package ru.milandr.khaidukov;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class BDWork {

    private static Integer getMaxID(ResultSet rs) throws SQLException {
        Integer max = Integer.MIN_VALUE;

        try {
            while (rs.next()) {
                Integer currID = rs.getInt("id");

                max = Math.max(max, currID);
            }
        } catch (SQLException e){ throw e; }

        return max;
    }

    private static double getAverageIndex (ResultSet rs) throws SQLException {
        Integer summ = 0;
        Integer count = 0;

        try {
            while (rs.next()) {
                try {
                    summ += Integer.parseInt(rs.getString("postal_code"));
                    count++;
                } catch (NumberFormatException e) {
                }
            }
        } catch (SQLException e){ throw e; }

        if (count == 0)
            return 0;

        return summ / count;
    }

    private static ArrayList <String> getSurnames(ResultSet rs) throws SQLException {
        ArrayList<String> surnames = new ArrayList<>();

        try {
            while (rs.next()) {
                surnames.add(rs.getString("last_name"));
            }
        } catch (SQLException e) { throw e; }

        return surnames;
    }

    public static void main (String[] args) throws SQLException{
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "thewall1979");

            if (conn == null) {
                System.out.println("No DB connection");
                System.exit(0);
            }

            System.out.println ("DB is successfully opened");

            ///*                           First task                                *///
            /////////////////////////////////////////////////////////////////////////////
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            Integer maxID = getMaxID (rs);

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, maxID);
            rs = ps.executeQuery();

            while (rs.next()){
                System.out.println("User with the biggest ID = " + maxID +
                        "\nname: " + rs.getString("first_name") +
                        "\nsurname: " + rs.getString("last_name") +
                        "\naddress id: " + rs.getInt("address_id") +
                        "\nphone number: " + rs.getString("phone_number"));
            }

            ///*                           Third task                                *///
            /////////////////////////////////////////////////////////////////////////////
            rs = stmt.executeQuery("SELECT * FROM users");
            ArrayList <String> surname = getSurnames (rs);
            Collections.sort(surname);

            System.out.println("\n\tSorted by surnames users:");

            for (String snm : surname){
                ps = conn.prepareStatement("SELECT * FROM users WHERE last_name = ?");
                ps.setString(1, snm);
                rs = ps.executeQuery();
                while (rs.next()){
                    System.out.println(rs.getInt("id") + " " +
                            rs.getString("first_name") + " " +
                            rs.getString("last_name") + " " +
                            rs.getString("phone_number"));
                }
            }

            ///*                           Fourth task                               *///
            /////////////////////////////////////////////////////////////////////////////
            rs = stmt.executeQuery("SELECT * FROM addresses");
            System.out.println("\nAverage value of postal code is " + getAverageIndex(rs));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
