package ru.milandr.courses.Trimbach;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class users_database {

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


            /*----------------------1---------------------*/

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM users  ");
            int max = getMax(resultSet.getInt("id"));

            identification.add(resultSet.getInt("id"));
            while (resultSet.next())
                if (resultSet.getInt("id") = max)
                    System.out.println(resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name") + ": id =" + " " + resultSet.getString("id")
                        + "; phone number is" + " "
                        + resultSet.getString("phone_number")
                        + "; address id is " + resultSet.getString("address_id"));



            /*----------------------2---------------------------*/

            resultSet = stmt.executeQuery("SELECT * FROM users " +
                    "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'"
                    +"INNER users JOIN addresses ON users.address_id = addresses.id ");
            System.out.println("Residents of P.O. Box 677, 8665 Ante Road are :");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name") + ": id = " + resultSet.getString("id") +
                        "; phone number is " + resultSet.getString("phone_number"));
            }

            /*--------------------3-----------------------------*/


            resultSet = stmt.executeQuery("SELECT last_name FROM users");
            while (resultSet.next()) {
                surname.add(resultSet.getString("last_name"));
            }
            Collections.sort(surname);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE users.last_name = ?");



            while (resultSet.next()) {
                System.out.println(resultSet.getString("last_name") + " "
                        + resultSet.getString("first_name") + ": id is "
                        + resultSet.getString("id")
                        + " phone number is " + resultSet.getString("phone_number") +
                        " address id is " + resultSet.getString("address_id"));
            }

            /*----------------------4---------------------------------*/
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

            /*---------------------------------5------------------------------*/

            resultSet = stmt.executeQuery("SELECT address FROM addresses INNER JOIN users" +
                    " ON addresses.id = users.address_id WHERE users.address_id = NULL GROUP BY addresses.address");
            while (resultSet.next()) {
                addressId.add(resultSet.getInt("id"));
            }
            System.out.println(resultSet.getString("address"));

            /*------------------exit-------------------------------------------*/

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