package ru.milandr.courses.polishchuk;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

class SQLDemo {
    private Connection connection;

    private class User {
        BigDecimal id;
        String first_name;
        String last_name;
        String phone_number;

        User(BigDecimal id, String first_name, String last_name, String phone_number) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone_number = phone_number;
        }

        User(User user) {
            this.id = user.id;
            this.last_name = user.last_name;
            this.first_name = user.first_name;
            this.phone_number = user.phone_number;
        }

        void printUser() {
            System.out.print("user id: " + this.id + '\n' +
                    "first_name: " + this.first_name + '\n' +
                    "last_name: " + this.last_name + '\n' +
                    "phone_number: " + this.phone_number + '\n');
        }
    }

    private class Address {
        BigDecimal id;
        String address;
        String city;
        String postal_code;

        Address(BigDecimal id, String address, String city, String postal_code) {
            this.id = id;
            this.address = address;
            this.city = city;
            this.postal_code = postal_code;
        }

        void printAddress() {
            System.out.print("address id: " + this.id + '\n' +
                    "address: " + this.address + '\n' +
                    "city: " + this.city + '\n' +
                    "postal_code: " + this.postal_code + '\n');
        }
    }

    private class AddressedUser extends User {
        Address address;

        AddressedUser(User user, Address address) {
            super(user);
            this.address = address;
        }

        AddressedUser(BigDecimal id, String first_name, String last_name, String phone_number, Address address) {
            super(id, first_name, last_name, phone_number);
            this.address = address;
        }

        @Override
        void printUser() {
            super.printUser();
            this.address.printAddress();
        }
    }

    public SQLDemo(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    private void printUsers(ArrayList<User> users) {
        for (User user : users) {
            user.printUser();
        }
    }

    private User initUserFromResultSet(ResultSet user) throws SQLException {
        return new User(
                user.getBigDecimal("id"),
                user.getString("first_name"),
                user.getString("last_name"),
                user.getString("phone_number")
        );
    }

    private Address initAddressFromResultSet(ResultSet address) throws SQLException {
        return new Address(
                address.getBigDecimal("address_id"),
                address.getString("address"),
                address.getString("city"),
                address.getString("postal_code")
        );
    }

    private AddressedUser initAddressedUserFromResultSet(ResultSet user) throws SQLException {
        return new AddressedUser(
                initUserFromResultSet(user),
                initAddressFromResultSet(user)
        );
    }

    private void printUserWithIdJava(BigDecimal id) {
        try (PreparedStatement stmtSelectUsersIds = connection.prepareStatement(
                "select users.*, addresses.id as addr_id, address, city, postal_code " +
                        "from users " +
                        "join addresses on users.address_id = addresses.id " +
                        "where users.id = ?"
        )) {
            stmtSelectUsersIds.setBigDecimal(1, id);

            ResultSet user = stmtSelectUsersIds.executeQuery();
            if (!user.next())
                throw new SQLException("Zero users found!");
            if (!user.isLast())
                throw new SQLException("Duplicate user id found!");
            AddressedUser userWithId = initAddressedUserFromResultSet(user);
            userWithId.printUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printUserWithMaxIdJava() {
        try (PreparedStatement stmtSelectUsersIds = connection.prepareStatement("select id from users")) {
            BigDecimal maxId = new BigDecimal(-1);
            ResultSet userIds = stmtSelectUsersIds.executeQuery();

            while (userIds.next()) {
                BigDecimal id = userIds.getBigDecimal("id");
                if (maxId.compareTo(id) < 0) {
                    maxId = new BigDecimal(String.valueOf(id));
                }
            }
            printUserWithIdJava(maxId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printUsersFromAddress(String address) {
        try (PreparedStatement stmtSelectUsersFromAddress = connection.prepareStatement(
                "select users.*, a.id as address_id, address, city, postal_code " +
                        "from users join addresses a on users.address_id = a.id " +
                        "where address = ?"
        )) {
            stmtSelectUsersFromAddress.setString(1, address);
            ResultSet resultSet = stmtSelectUsersFromAddress.executeQuery();

            while (resultSet.next()) {
                AddressedUser addressedUser = initAddressedUserFromResultSet(resultSet);
                addressedUser.printUser();
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printUsersSortedByLastName() {
        try (PreparedStatement stmtSelectUsers = connection.prepareStatement("select * from users")) {
            ResultSet resultSet = stmtSelectUsers.executeQuery();

            ArrayList<User> usersArrayList = new ArrayList<>();
            while (resultSet.next()) {
                User user = initUserFromResultSet(resultSet);
                usersArrayList.add(user);
            }
            usersArrayList.sort((o1, o2) -> o1.last_name.compareTo(o2.last_name));
            usersArrayList.forEach(u -> System.out.println(u.last_name + ' ' + u.first_name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcAvgPostalCode() {
        double sum = 0;
        int cnt = 0;

        try (PreparedStatement stmtSelectPostalCodes = connection.prepareStatement(
                "select postal_code from addresses"
        )) {
            ResultSet resultSet = stmtSelectPostalCodes.executeQuery();

            while (resultSet.next()) {
                String code = resultSet.getString("postal_code");
                if (code.matches("^[0-9]+$")) {
                    sum += Integer.parseInt(code);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (cnt == 0)
            return 0.;
        return sum / cnt;
    }

    public void findUnusedAddr() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select address from addresses left join users on users.address_id = addresses.id where users.id is null"
        )) {
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try (Connection dbConn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/users_database",
                "postgres", "postgres")) {
            SQLDemo demo = new SQLDemo(dbConn);

            demo.printUserWithMaxIdJava();
            System.out.println();

            demo.printUsersFromAddress("P.O. Box 677, 8665 Ante Road");
            System.out.println();

            demo.printUsersSortedByLastName();
            System.out.println();

            System.out.println(demo.calcAvgPostalCode());

            demo.findUnusedAddr();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}