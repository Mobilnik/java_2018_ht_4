package ru.milandr.courses.pavlyuk;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTask4 {
    public static void main(String[] args) {

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users_database",
                "postgres", "postgres");
             Statement smt = con.createStatement()) {
            Class.forName("org.postgresql.Driver");

            // 1. Найти средствами джавы (!) пользователя с наибольшим id.
            // Просто найти пользователя по условию id = 100 не будет считаться решением.
            // Вывести всю информацию о нем в консоль.

            HomeTask4Methods.method1(smt);

            //2. Вывести всех пользователей, проживающих по адресу P.O. Box 677, 8665 Ante Road.
            // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN.

            HomeTask4Methods.method2(smt);

            //3. Вывести всех пользователей, отсортировав их средствами джавы (!) в алфавитном порядке по их фамилии.

            HomeTask4Methods.method3(smt);


            // 4. Найти средствами джавы (!) среднее значение почтового индекса, учитывая только числовые значения индекса.

            HomeTask4Methods.method4(smt);

            //5. Найти адрес, по которому не проживает ни один пользователь.
            // Для того, чтобы это сделать необходимо самостоятельно освоить такую SQL-конструкцию как JOIN

            HomeTask4Methods.method5(smt);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("Проблема при работе с БД!");
            System.exit(0);
        }
    }
}

