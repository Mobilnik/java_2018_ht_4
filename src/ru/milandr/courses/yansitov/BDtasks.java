package ru.milandr.courses;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class BDtasks {
    private static Connection conn;

    private static int findMaxID() throws SQLException
    {

        int maxID = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                maxID = Math.max(rs.getInt("id"), maxID);
            }
        }
        catch (SQLException e){
            throw e;
        }

        return maxID;
    }


    private static void writeInformationAboutUserWithID(int ID) throws SQLException {
        PreparedStatement stmtUsers = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
        stmtUsers.setInt(1, ID);
        ResultSet rs = stmtUsers.executeQuery();


        rs.next();
        System.out.print(
                 "ID: " + ID +
                "\nfirst_name: " + rs.getString("first_name") + "; " +
                "\nlast_name: " + rs.getString("last_name") + "; " +
                "\nphone number: " + rs.getString("phone_number")+"; ");


        int addressID = rs.getInt("address_id");
        PreparedStatement stmtAdresses = conn.prepareStatement("SELECT * FROM addresses WHERE id = ?");
        stmtAdresses.setInt(1,addressID);
        rs = stmtAdresses.executeQuery();

        rs.next();
        System.out.println(
                "\naddress: " + rs.getString("address") + "; "+
                "\ncity: " + rs.getString("city") + "; " +
                "\npostal_code: " + rs.getString("postal_code") +"; ");
    }


    public static void firstTask() throws SQLException {
        System.out.println("\n ______ПЕРВОЕ_ЗАДАНИЕ_____\n");

        try {
          int maxID = findMaxID();
          writeInformationAboutUserWithID(maxID);
        }
        catch (SQLException e)
        {
            throw e;
        }
    }


    public static void secondTask() throws SQLException
    {
        System.out.println("\n ______ВТОРОЕ_ЗАДАНИЕ_____\n");


        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT users.first_name, users.last_name FROM users\n" +
                    "JOIN addresses ON users.address_id = addresses.id\n" +
                    "WHERE addresses.address = 'P.O. Box 677, 8665 Ante Road'\n");

            System.out.println("Люди, живущие на P.O. Box 677, 8665 Ante Road");

            while (rs.next()){
                System.out.println(
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") + " " );
            }
        }catch (SQLException e){
            throw e;
        }
    }

    private static ArrayList <String> getSortLastNames() throws SQLException {
        ArrayList<String> lastNames = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                lastNames.add(rs.getString("last_name"));
            }

            Collections.sort(lastNames);
        } catch (SQLException e) { throw e; }


        return lastNames;
    }

    private static void writeInformationAboutUserLastName(String lastName) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE last_name = ?");
            stmt.setString(1, lastName);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                System.out.println(rs.getString("last_name") + " " + rs.getString("first_name"));
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void thirdTask() throws SQLException {
        System.out.println("\n ______ТРЕТЬЕ_ЗАДАНИЕ_____\n");

        try
        {
            ArrayList<String> lastNames = getSortLastNames();

            for (String it : lastNames){
                writeInformationAboutUserLastName(it);
            }
        }
        catch (SQLException e)
        {
            throw e;
        }

    }


    public static void fourthTask() throws SQLException
    {
        System.out.println("\n ______ЧЕТВЁРТОЕ_ЗАДАНИЕ_____\n");

        try {
            double summ    = 0;
            int    quatity = 0;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM addresses");

            while (rs.next()) {
                try {
                    summ += (double)Integer.parseInt(rs.getString("postal_code"));
                    quatity ++;
                }
                catch (NumberFormatException e){}
            }

            System.out.println("среднее значение индекса = "+ summ/quatity);
        } catch (SQLException e) {
            throw e;
        }
    }


    public static void fifthTask() throws SQLException
    {
        System.out.println("\n ______ПЯТОЕ_ЗАДАНИЕ_____\n");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT addresses.id, addresses.address FROM addresses\n" +
                    "FULL OUTER JOIN users on addresses.id = users.address_id\n" +
                    "WHERE users.address_id is null\n");

            while (rs.next()){
                System.out.println(rs.getString("address") + "\n");
            }
        }catch (SQLException e){
            throw e;
        }
    }


    public static void main(String[] args) throws SQLException{

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "160206220");
            Class.forName("org.postgresql.Driver");

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }

            firstTask();
            secondTask();
            thirdTask();
            fourthTask();
            fifthTask();

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
}
