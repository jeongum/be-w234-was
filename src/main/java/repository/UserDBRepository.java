package repository;

import exception.UserException;
import exception.UserExceptionMessage;
import model.User;
import repository.factory.GlobalEntityMangerFactory;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class UserDBRepository implements UserRepository {
    private EntityManager em;
    private static UserDBRepository instance = new UserDBRepository();

    private UserDBRepository() {
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
            tx.rollback();
            throw new UserException(UserExceptionMessage.DUPLICATE_USER);
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
        return Optional.ofNullable(user);
    }

    @Override
    public void deleteAll() {
        createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("delete from User u").executeUpdate();

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
        createEntityManager();

        String countStr = em.createQuery("select count(u) from User u").getSingleResult().toString();

        closeEntityManager();
        return Integer.parseInt(countStr);
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
        em = GlobalEntityMangerFactory.getInstance().getEmf().createEntityManager();
    }
}
