package repository;

import model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class UserDBRepository implements UserRepository {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static UserDBRepository instance = new UserDBRepository();

    private UserDBRepository() {
        this.emf = Persistence.createEntityManagerFactory("java-was-2022");
    }

    public static UserDBRepository getInstance() {
        return instance;
    }

    @Override
    public User save(User user) {
        createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(user);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            closeEntityManager();
        }

        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        createEntityManager();

        User user = em.find(User.class, userId);

        closeEntityManager();
        return Optional.of(user);
    }

    @Override
    public void deleteAll() {
        createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("delete from User u", User.class);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public int count() {
        Query query = em.createQuery("select count(u) from User u", User.class);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<User> findAll() {
        createEntityManager();

        List<User> users = em.createQuery("select u from User u", User.class).getResultList();

        closeEntityManager();
        return users;
    }

    private void closeEntityManager() {
        em.close();
    }

    private void createEntityManager() {
        em = emf.createEntityManager();
    }
}
