package repository.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GlobalEntityMangerFactory {
    private EntityManagerFactory emf;
    private static GlobalEntityMangerFactory instance = new GlobalEntityMangerFactory();

    private GlobalEntityMangerFactory() {
        this.emf = Persistence.createEntityManagerFactory("java-was-2022");
    }

    public static GlobalEntityMangerFactory getInstance() {
        return instance;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
