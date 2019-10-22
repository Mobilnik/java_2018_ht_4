package ru.milandr.courses.pavlyuk;
import java.sql.*;

import java.util.*;
import java.util.stream.IntStream;

public class CompDatabase {


    public static boolean compareData(String db1Link,
                                      String db2Link,
                                      String table1Name,
                                      String table2Name,
                                      String columns) {

        HashMap<String, List<String>> table1 = new HashMap<>();

        HashMap<String, List<String>> table2 = new HashMap<>();
        List<String> requestedColumns = Arrays.asList(columns.split(","));


        requestedColumns.forEach(col -> {
            table1.put(col, new ArrayList<>());
            table2.put(col, new ArrayList<>());
        });

        try (Connection con = DriverManager.getConnection(db1Link,
                "postgres", "postgres");
             Connection con2 = DriverManager.getConnection(db2Link,
                     "postgres", "postgres");
             Statement smt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_READ_ONLY);
             Statement smt2 = con2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            Class.forName("org.postgresql.Driver");

            ResultSet rs = smt.executeQuery("SELECT * FROM " + table1Name);
            ResultSet rs2 = smt2.executeQuery("SELECT * FROM " + table2Name);

            rs.last();
            int numberOfRowsTable1 = rs.getRow();

            rs2.last();
            int numberOfRowsTable2 = rs2.getRow();

            if (numberOfRowsTable1 != numberOfRowsTable2) {
                return false;
            }

            IntStream stream = IntStream.range(1, numberOfRowsTable1);
            stream.forEach(e -> requestedColumns.forEach(col -> {
                try {
                    rs.absolute(e);
                    rs2.absolute(e);
                    table1.get(col).add(rs.getString(col));
                    table2.get(col).add(rs2.getString(col));

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Введенные колонки не найдены");
                    System.exit(0);

                }
            }));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("Проблема при работе с БД!");
            System.exit(0);
        }

        return (table1.equals(table2)); }
}
