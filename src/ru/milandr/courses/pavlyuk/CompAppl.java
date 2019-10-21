package ru.milandr.courses.pavlyuk;

public class CompAppl {
    public static void main(String[] args) {
        boolean a = CompDatabase.compareData("jdbc:postgresql://localhost:5432/users_database",
                "jdbc:postgresql://localhost:5432/users_database2",
                "users",
                "users",
                "id,last_name");
        System.out.println(a);
    }
}
