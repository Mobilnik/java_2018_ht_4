package ru.milandr.courses.pavlyuk;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTask4Methods {

    /**
     * Создает коллекцию из объектов класса Users по переданным полям из таблицы users базы данных users_database.
     *
     * @param rs множетсва результатов данных, полученных в результате SQL – запроса.
     * @return коллекция из объектов класса Users, полученная после обработки результата rs.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    private static List<User> selectAllData(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getInt("address_id"),
                    rs.getString("phone_number"));
            users.add(user);
        }
        return users;
    }

    /**
     * Находит в таблице users пользователя c наибольшим id и выводит всю информацию о нем.
     *
     * @param smt объект типа Statement, служащий для исполнения запросов к базе данных users_database.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    public static void method1(Statement smt) throws SQLException {
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");
        List<User> users = selectAllData(rs);
        System.out.println(Collections.max(users, Comparator.comparing(User::getId)));
    }

    /**
     * Находит в таблице users всех пользователей, проживающих по адресу P.O. Box 677, 8665 Ante Road и выводит.
     *
     * @param smt объект типа Statement, служащий для исполнения запросов к базе данных users_database.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    public static void method2(Statement smt) throws SQLException {
        ResultSet rs = smt.executeQuery("SELECT * " +
                "FROM users INNER JOIN addresses " +
                "ON users.address_id = addresses.id " +
                "WHERE address = 'P.O. Box 677, 8665 Ante Road';");
        List<User> usersLivingAtAnteRoad = selectAllData(rs);
        System.out.println(usersLivingAtAnteRoad.toString());
    }

    /**
     * Сортирует в алфавитном порядке по фамилии пользователей из таблицы users и выводит.
     *
     * @param smt объект типа Statement, служащий для исполнения запросов к базе данных users_database.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    public static void method3(Statement smt) throws SQLException {
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");
        List<User> users = selectAllData(rs);

        users.sort(Comparator.comparing(User::getLastName));
        System.out.println(users.toString());
    }

    /**
     * Находит в таблице users среднее значение почтового индекса.
     *
     * @param smt объект типа Statement, служащий для исполнения запросов к базе данных users_database.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    public static void method4(Statement smt) throws SQLException {
        Double averageAddress;
        ResultSet rs = smt.executeQuery("SELECT * FROM users;");
        List<User> users = selectAllData(rs);

        averageAddress = users.stream().collect(Collectors.averagingDouble(User::getAddressId));
        System.out.println(averageAddress);
    }

    /**
     * Находит в базе данных users_database адрес из таблицы addresses, по которому не проживает ни один пользователь из таблицы users.
     *
     * @param smt объект типа Statement, служащий для исполнения запросов к базе данных users_database.
     * @throws SQLException Может возникнуть при вызове метода selectAllData.
     */
    public static void method5(Statement smt) throws SQLException {
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
