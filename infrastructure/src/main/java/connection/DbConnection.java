package connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {

    private static final DbConnection connection = new DbConnection();

    private DbConnection() {}

    public static DbConnection getInstance() { return connection; }

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");

    public EntityManagerFactory getEntityManagerFactory() { return entityManagerFactory; }

    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
