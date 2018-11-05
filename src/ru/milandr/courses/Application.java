package ru.milandr.courses;

import ru.milandr.courses.Tasks;

import java.sql.*;

public class Application  {
    public static void main(String[] args) {
        //System.out.println("Hello. This is Home Task #4");
        Tasks tasks = new Tasks();
        try{tasks.task1();}
        catch (SQLException exc){exc.printStackTrace();}

        try{tasks.task2();}
        catch (SQLException exc){exc.printStackTrace();}

        try{tasks.task3();}
        catch (SQLException exc){exc.printStackTrace();}

        try{tasks.task4();}
        catch (SQLException exc){exc.printStackTrace();}

        try{tasks.task5();}
        catch (SQLException exc){exc.printStackTrace();}
    }
}
