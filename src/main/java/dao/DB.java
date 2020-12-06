package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DB {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hmsPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost/hms", "root", "");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


}