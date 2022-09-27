package repository;

import model.Memo;
import model.emf.EntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MemoH2Repository implements MemoRepository {
    private EntityManager em;
    private static MemoH2Repository instance = new MemoH2Repository();

    private MemoH2Repository() {
    }

    public static MemoH2Repository getInstance() {
        return instance;
    }

    @Override
    public void save(Memo memo) {
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
        em = EntityManagerFactory.emf.createEntityManager();
    }
}
