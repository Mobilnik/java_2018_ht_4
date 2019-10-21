package ru.milandr.courses.pavlyuk;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainJava {
    public static void main(String[] args) {

        List<User> Users = new ArrayList<>();
        List<User> AnteRoadUsers = new ArrayList<>();
        Double averageAddress;
        String addressWithoutUser = null;

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users_database",
                "postgres", "postgres");
             Statement smt = con.createStatement()) {
            Class.forName("org.postgresql.Driver");

            // 1. Найти средствами джавы (!) пользователя с наибольшим id.
            // Просто найти пользователя по условию id = 100 не будет считаться решением.
            // Вывести всю информацию о нем в консоль.

            ResultSet rs = smt.executeQuery("SELECT * FROM users;");

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getInt("address_id"),
                        rs.getString("phone_number"));
                Users.add(user);

            }

            System.out.println(Collections.max(Users, Comparator.comparing(User::getId)));

            //2. Вывести всех пользователей, проживающих по адресу P.O. Box 677, 8665 Ante Road.
            // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN.

            rs = smt.executeQuery("SELECT *\n" +
                    "FROM users INNER JOIN addresses\n" +
                    "ON users.address_id = addresses.id\n" +
                    "WHERE address = 'P.O. Box 677, 8665 Ante Road';");

            while (rs.next()) {
                User userID = new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getInt("address_id"),
                        rs.getString("phone_number"));
                AnteRoadUsers.add(userID);
            }

            System.out.println(AnteRoadUsers.toString());

            //3. Вывести всех пользователей, отсортировав их средствами джавы (!) в алфавитном порядке по их фамилии.


            Users.sort((Comparator.comparing(User::getLastName)));
            System.out.println(Users.toString());

            // 4. Найти средствами джавы (!) среднее значение почтового индекса, учитывая только числовые значения индекса.

           averageAddress = Users.stream().collect(Collectors.averagingDouble(User::getAddressID));
            System.out.println(averageAddress);

            //5. Найти адрес, по которому не проживает ни один пользователь.
            // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN
                rs = smt.executeQuery("SELECT address\n" +
                        "FROM users RIGHT JOIN addresses\n" +
                        "ON users.address_id = addresses.id\n" +
                        "WHERE first_name is NULL");

                while (rs.next()) {
                    addressWithoutUser = rs.getString(1);
                }

                System.out.println(addressWithoutUser == null ? "null" : addressWithoutUser);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("Проблема при работе с БД!");
            System.exit(0);
        }
    }
}

