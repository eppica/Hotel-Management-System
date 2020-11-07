package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DB {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hmsPU");

    public static EntityManager getConnection() {
        return emf.createEntityManager();
    }


}
