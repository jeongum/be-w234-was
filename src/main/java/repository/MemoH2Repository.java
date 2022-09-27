package repository;

import model.Memo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class MemoH2Repository implements MemoRepository {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static MemoH2Repository instance = new MemoH2Repository();

    private MemoH2Repository() {
        this.emf = Persistence.createEntityManagerFactory("java-was-2022");
    }

    public static MemoH2Repository getInstance() {
        return instance;
    }

    @Override
    public Memo save(Memo memo) {
        createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(memo);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            closeEntityManager();
        }

        return memo;
    }

    @Override
    public List<Memo> findAll() {
        createEntityManager();

        List<Memo> memos = em.createQuery("select m from Memo m", Memo.class).getResultList();

        closeEntityManager();
        return memos;
    }

    private void closeEntityManager() {
        em.close();
    }

    private void createEntityManager() {
        em = emf.createEntityManager();
    }
}
