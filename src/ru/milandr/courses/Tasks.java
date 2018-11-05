package ru.milandr.courses;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.Math.toIntExact;

public class Tasks{
    public void task1() throws SQLException{
        System.out.println("\nTASK #1\n");
        Connection connect = null;
        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234"
            );

            if(connect == null){
                System.out.println("Not connected");
                System.exit(1);
            }

            ResultSet resultSet;
            PreparedStatement statement = connect.prepareStatement(
                    "SELECT * FROM users",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery();

            long maxID = 0;

            while(resultSet.next()){
                if(resultSet.getInt("id") > maxID){
                    maxID = resultSet.getLong("id");
                }
            }
            resultSet.beforeFirst();
            while (resultSet.next() && resultSet.getInt("id") < maxID){}
            System.out.println(resultSet.getLong("id") +
                    " " + resultSet.getString("first_name") +
                    " " + resultSet.getString("last_name") +
                    " " + resultSet.getLong("address_id") +
                    " " + resultSet.getLong("phone_number"));

        }
        catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if(connect != null){
                connect.close();
            }
        }
    }

    public void task2() throws SQLException {
        System.out.println("\nTASK #2\n");
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234"
            );

            if (connect == null) {
                System.out.println("Not connected");
                System.exit(1);
            }

            ResultSet resultSet;
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM users " +
                            "INNER JOIN addresses ON addresses.address = " +
                            "'P.O. Box 677, 8665 Ante Road' AND " +
                            "users.address_id = addresses.id"
            );
            resultSet = prepare.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getLong("id") +
                        " " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") +
                        " " + resultSet.getLong("address_id") +
                        " " + resultSet.getLong("phone_number"));

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.close();
            }
        }
    }

    final class MyEntry<K, V> implements Map.Entry<K, V> {
        public final K key;
        public V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }

    public void task3() throws SQLException {
        System.out.println("\nTASK #3\n");
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234"
            );

            if (connect == null) {
                System.out.println("Not connected");
                System.exit(1);
            }

            ResultSet resultSet;
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM users",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = prepare.executeQuery();
            List<Entry<Long, String>> list = new ArrayList<>();

            long i = 0;
            while(resultSet.next()){
                Entry entry = new MyEntry(i,
                        resultSet.getString("first_name"));
                i++;
                list.add(entry);
            }
            list.sort(Entry.comparingByValue());

            for(Entry<Long, String> j : list){
                if(!resultSet.absolute(toIntExact(j.getKey())+1)){
                    System.out.println("ERROR in sort\n");
                    System.exit(1);
                }
                System.out.println(resultSet.getLong("id") +
                        " " + resultSet.getString("first_name") +
                        " " + resultSet.getString("last_name") +
                        " " + resultSet.getLong("address_id") +
                        " " + resultSet.getLong("phone_number"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.close();
            }
        }
    }

    public void task4() throws SQLException {
        System.out.println("\nTASK #4\n");
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234"
            );

            if (connect == null) {
                System.out.println("Not connected");
                System.exit(1);
            }

            ResultSet resultSet;
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM addresses"
            );
            resultSet = prepare.executeQuery();
            int summ = 0, quantity = 0;
            while(resultSet.next()){
                try {
                    summ += Integer.parseInt(resultSet.getString("postal_code"));
                    //Не знаю, насколько строка, начинающаяся с нуля является числом,
                    //но приведение к инту прошло
                    quantity++;
                }
                catch (NumberFormatException e){continue;}
            }

            if(summ*quantity == 0)
            {
                System.out.println("ERROR with summ or quantity");
            }
            double res = summ/quantity;
            System.out.println(res);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.close();
            }
        }
    }

    public void task5() throws SQLException {
        System.out.println("\nTASK #5\n");
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234"
            );

            if (connect == null) {
                System.out.println("Not connected");
                System.exit(1);
            }

            ResultSet resultSet;
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM addresses " +
                            "LEFT outer join users u " +
                            "on addresses.id = u.address_id " +
                            "where u.address_id is null"
            );
            resultSet = prepare.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getLong("id") +
                        " " + resultSet.getString("address") +
                        " " + resultSet.getString("city") +
                        " " + resultSet.getString("postal_code"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.close();
            }
        }
    }
}

