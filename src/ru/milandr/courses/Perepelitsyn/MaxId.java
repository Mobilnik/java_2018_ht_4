package ru.milandr.courses;
import java.sql.*;

public class MaxId {
    public static Integer getMaxId(ResultSet result) throws SQLException {
        Integer max = Integer.MIN_VALUE;

        try {
            while (result.next()) {
                Integer currID = result.getInt("id");

                max = Math.max(max, currID);
            }
        } catch (SQLException e) {
            throw e;
        }

        return max;
    }
}
