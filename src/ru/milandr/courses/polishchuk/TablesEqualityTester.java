package ru.milandr.courses.polishchuk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

public class TablesEqualityTester {
    private Connection db1Connection;
    private Connection db2Connection;
    private String table1Name;
    private String table2Name;
    private String columnNames;
    private int columnsNum;

    public TablesEqualityTester(Connection db1Connection, Connection db2Connection,
                                String table1Name, String table2Name,
                                String columnNames) {
        this.db1Connection = db1Connection;
        this.db2Connection = db2Connection;
        this.table1Name = table1Name;
        this.table2Name = table2Name;
        this.columnNames = columnNames;
        this.columnsNum = columnNames.split(",").length;
    }

    private ArrayList<ArrayList<String>> initArrayFromResultSet(ResultSet resultSet, int size) throws SQLException {
        ArrayList<ArrayList<String>> dbEntries = new ArrayList<ArrayList<String>>();

        IntStream.range(0, size).forEach(i -> {
            try {
                resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            ArrayList<String> list = new ArrayList<String>();
            IntStream.range(1, columnsNum + 1).forEach(
                    col -> {
                        String value = null;
                        try {
                            value = resultSet.getString(col);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.exit(-1);
                        }
                        list.add(value);
                    }
            );
            dbEntries.add(list);
        });
        return dbEntries;
    }

    public boolean equalityTest() throws SQLException {
        ResultSet count1 = db1Connection.prepareStatement("select count(*) from " + table1Name).executeQuery();
        ResultSet count2 = db2Connection.prepareStatement("select count(*) from " + table2Name).executeQuery();
        count1.next();
        count2.next();
        int size1 = Integer.parseInt(count1.getString(1));
        int size2 = Integer.parseInt(count2.getString(1));

        if (size1 != size2) {
            return false;
        }
        if (size1 == 0) {
            return true;
        }

        PreparedStatement stmtDB1 = db1Connection.prepareStatement("select " + columnNames + " from " + table1Name);
        PreparedStatement stmtDB2 = db2Connection.prepareStatement("select " + columnNames + " from " + table2Name);
        ResultSet resultSet1 = stmtDB1.executeQuery();
        ResultSet resultSet2 = stmtDB2.executeQuery();

        ArrayList<ArrayList<String>> db1Entries = initArrayFromResultSet(resultSet1, size1);
        ArrayList<ArrayList<String>> db2Entries = initArrayFromResultSet(resultSet2, size2);

        db1Entries.sort((t1, t2) ->
                IntStream.range(1, columnsNum).reduce(0, (acc, j) -> acc == 0 ? t1.get(j).compareTo(t2.get(j - 1)) : acc)
        );
        db2Entries.sort((t1, t2) ->
                IntStream.range(1, columnsNum).reduce(0, (acc, j) -> acc == 0 ? t1.get(j).compareTo(t2.get(j - 1)) : acc)
        );

        int result = IntStream.range(0, size1).reduce(0,
                (acc, i) -> acc == 0 ?
                        IntStream.range(0, columnsNum).reduce(0,
                                (innerAcc, j) -> innerAcc == 0 ?
                                        db1Entries.get(i).get(j).compareTo(db2Entries.get(i).get(j)) : innerAcc
                        ) : acc
        );
        return result == 0;
    }
}