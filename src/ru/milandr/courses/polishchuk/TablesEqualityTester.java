package ru.milandr.courses.polishchuk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TablesEqualityTester {
    private Connection db1Connection;
    private Connection db2Connection;
    private String table1Name;
    private String table2Name;
    private ArrayList<String> columnNames;

    public TablesEqualityTester(Connection db1Connection, Connection db2Connection,
                                String table1Name, String table2Name,
                                String columns) {
        this.db1Connection = db1Connection;
        this.db2Connection = db2Connection;
        this.table1Name = table1Name;
        this.table2Name = table2Name;
        this.columnNames = new ArrayList<String>();
        Arrays.stream(columns.split(",")).forEach(col -> columnNames.add(col));
    }

    public boolean equalityTest() throws SQLException {
        String request = "select " +
                columnNames.stream().reduce("", (s1, s2)-> s1 + "," + s2).toString() +
                "from ?";
        PreparedStatement stmtDB1 = null;
        PreparedStatement stmtDB2 = db1Connection.prepareStatement(
                "select " +
                        columnNames.stream().reduce("", (s1, s2)-> s1 + "," + s2).toString() +
                "");
    }
}