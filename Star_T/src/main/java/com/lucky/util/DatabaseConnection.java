package com.lucky.util;

import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        String connectionUrl= "jdbc:mysql://rm-bp161s1z7rbbuzm0xgo.mysql.rds.aliyuncs.com:3306/themstar";

        ResultSet resultSet;

        try (Connection connection= DriverManager.getConnection(connectionUrl,"liukaiwen","fyqWu1-rapweh-tuvnah");
             Statement statement = connection.createStatement()) {

            String selectSql = "SELECT * FROM `user`";            //输入希望执行的SQL。
            resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
