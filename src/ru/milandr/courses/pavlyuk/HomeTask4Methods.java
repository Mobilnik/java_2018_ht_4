package ru.milandr.courses.pavlyuk;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTask4Methods {

    // 1. Найти средствами джавы (!) пользователя с наибольшим id.
    // Просто найти пользователя по условию id = 100 не будет считаться решением.
    // Вывести всю информацию о нем в консоль.

    public static void method1(Statement smt) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");

        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getInt("address_id"),
                    rs.getString("phone_number"));
            users.add(user);

        }

        System.out.println(Collections.max(users, Comparator.comparing(User::getId)));
    }

    //2. Вывести всех пользователей, проживающих по адресу P.O. Box 677, 8665 Ante Road.
    // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN.

    public static void method2(Statement smt) throws SQLException {

        List<User> usersLivingAtAnteRoad = new ArrayList<>();

        ResultSet rs = smt.executeQuery("SELECT * " +
                "FROM users INNER JOIN addresses " +
                "ON users.address_id = addresses.id " +
                "WHERE address = 'P.O. Box 677, 8665 Ante Road';");

        while (rs.next()) {
            User userID = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getInt("address_id"),
                    rs.getString("phone_number"));
            usersLivingAtAnteRoad.add(userID);
        }

        System.out.println(usersLivingAtAnteRoad.toString());
    }

    //3. Вывести всех пользователей, отсортировав их средствами джавы (!) в алфавитном порядке по их фамилии.
    public static void method3(Statement smt) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");

        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getInt("address_id"),
                    rs.getString("phone_number"));
            users.add(user);

        }

        users.sort((Comparator.comparing(User::getLastName)));
        System.out.println(users.toString());
    }

    // 4. Найти средствами джавы (!) среднее значение почтового индекса, учитывая только числовые значения индекса.

    public static void method4(Statement smt) throws SQLException {
        Double averageAddress;
        List<User> users = new ArrayList<>();
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");

        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getInt("address_id"),
                    rs.getString("phone_number"));
            users.add(user);

        }

        averageAddress = users.stream().collect(Collectors.averagingDouble(User::getAddressId));
        System.out.println(averageAddress);
    }

    //5. Найти адрес, по которому не проживает ни один пользователь.
    // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN

    public static void method5(Statement smt) throws SQLException{
        String addressWithoutUser = null;
        ResultSet rs = smt.executeQuery("SELECT address " +
                "FROM users RIGHT JOIN addresses " +
                "ON users.address_id = addresses.id " +
                "WHERE first_name is NULL;");

        while (rs.next()) {
            addressWithoutUser = rs.getString(1);
        }

        System.out.println(addressWithoutUser == null ? "null" : addressWithoutUser);

    }


}
