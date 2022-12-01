package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "1234";

    private static Connection connection = null;

   public static Connection getConnection() {
       try {
           connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           System.out.println("Successful connection!");
       } catch (SQLException e) {
           System.out.println("can't connect to database!");
       }
       return connection;
   }



}
