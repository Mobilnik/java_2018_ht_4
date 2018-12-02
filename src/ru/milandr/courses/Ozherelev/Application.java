package ru.milandr.courses.Ozherelev;

import java.sql.*;
import java.util.*;

public class Application {

    private static ArrayList<Integer> usersId = new ArrayList<>();
    private static ArrayList<String> usersSurnames = new ArrayList<>();
    private static ArrayList<Integer> postalCodes = new ArrayList<>();
    private static Set<Integer> adressIds = new HashSet<>();
    private static ResultSet resultSet;
    private static Connection connection = null;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static double averagePostalcode;


    private static void findMaxIdUser(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.id = ?");

            while (resultSet.next()) {
                usersId.add(resultSet.getInt("id"));
            }

            usersId.sort(Collections.reverseOrder());
            preparedStatement.setInt(1, usersId.get(0));

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("id = " + resultSet.getString("id") +
                        "; Name Surname: " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") + "; address_id: "
                        + resultSet.getString("address_id") + "; phone_number: "
                        + resultSet.getString("phone_number") );
            }

            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findAllUsersWithAddress()
    {
        try{

            resultSet = statement.executeQuery("SELECT * FROM users JOIN addresses ON " +
                    "addresses.id = users.address_id"
                    + " WHERE addresses.address =  'P.O. Box 677, 8665 Ante Road'");

            while(resultSet.next())
            {
               System.out.println("id = " + resultSet.getString("id") +
                        "; Name Surname: " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") + "; address_id: "
                        + resultSet.getString("address_id") + "; phone_number: "
                        + resultSet.getString("phone_number") );
            }

            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void printSortedAllUsers()
    {
        try{

            resultSet = statement.executeQuery("SELECT * FROM users");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.last_name = ?");

            while(resultSet.next())
            {
                usersSurnames.add(resultSet.getString("last_name"));
            }

            usersSurnames.sort(String.CASE_INSENSITIVE_ORDER);

            for (String usersSurname : usersSurnames) {
                preparedStatement.setString(1, usersSurname);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                System.out.println("id = " + resultSet.getString("id") +
                        "; Name Surname: " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") + "; address_id: "
                        + resultSet.getString("address_id") + "; phone_number: "
                        + resultSet.getString("phone_number"));
            }

            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void countAveragePostalIndex()
    {
        int counter = 0;
        double sum = 0.0;

        try {

            resultSet = statement.executeQuery("SELECT postal_code FROM addresses");

            while (resultSet.next())
            {
                if (canConvetToNumber(resultSet.getString("postal_code")))
                {
                    postalCodes.add(resultSet.getInt("postal_code"));
                    sum = sum + postalCodes.get(counter);
                    counter++;
                }
            }
            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (counter != 0) {
            averagePostalcode = sum / counter;
        }

        else
        {
            System.out.println("DIVISION BY ZERO IN countAveragePostalIndex");
            System.exit(0);
        }
    }

    private static boolean canConvetToNumber(String string)
    {
        try{
            Integer intFromstring = new Integer(string);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    private static void findAddressWithoutUsers()
    {
        try{

            ArrayList<Integer> listOfNullAdressesIds = new ArrayList<>();

            resultSet = statement.executeQuery("SELECT addresses.id FROM addresses INNER JOIN users "
                    + "ON users.address_id = addresses.id ");

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.address_id = ?");


            while(resultSet.next())
            {
                adressIds.add(resultSet.getInt("id"));
            }

            resultSet = statement.executeQuery("SELECT id FROM addresses");

            while (resultSet.next())
            {
                if (!adressIds.contains(resultSet.getInt("id")))
                {
                    listOfNullAdressesIds.add(resultSet.getInt("id"));
                }
            }

            if (listOfNullAdressesIds.size() == 0){
                System.out.println("Where aren`t empty addresses");
            }

            else
            {
                for (Integer listOfNullAdressesId : listOfNullAdressesIds) {
                    preparedStatement.setInt(1, listOfNullAdressesId);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    System.out.println("empty id = " + resultSet.getString("id"));
                }
            }


            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {
        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users_database",
                    "postgres", "password");


            if (connection == null) {
                System.out.println("No connection with DATABASE users_database");
                System.exit(-1);
            }

            statement = connection.createStatement();

            System.out.println("I've found the user with max id\n");
            findMaxIdUser();

            System.out.print("\n");

            System.out.println("I've all users with addres as Box 677, 8665 Ante Road.\n");
            findAllUsersWithAddress();

            System.out.print("\n");

            System.out.println("I've sorted users surnames in alphabet order\n");
            printSortedAllUsers();

            System.out.print("\n");
            System.out.println("I've counted average postal code\n");
            countAveragePostalIndex();
            System.out.printf("%.5f\n", averagePostalcode);

            System.out.print("\n");
            System.out.println("I've found empty addresses:\n");
            findAddressWithoutUsers();

            statement.close();
        }

        catch (SQLException sqlError)
        {
            sqlError.printStackTrace();
        }

        finally {
            if (connection != null){
                connection.close();
            }
        }
    }
}
