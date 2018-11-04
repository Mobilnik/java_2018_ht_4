package ru.milandr.courses;

import java.sql.*;
import java.util.ArrayList;

public class LastNameArray {
    public static ArrayList<String> getLastNames(ResultSet result) throws SQLException {
        ArrayList<String> surnames = new ArrayList<>();

        try {
            while (result.next()) {
                surnames.add(result.getString("last_name"));
            }
        } catch (SQLException e) {
            throw e;
        }

        return surnames;
    }
}
