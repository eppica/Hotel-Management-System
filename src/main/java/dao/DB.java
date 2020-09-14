package dao;

import java.sql.*;

public abstract class DB {

    public static Connection getConnection(){
        Connection connection = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(Connection connection, Statement statement){
        try {
            if(statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
