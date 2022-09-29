package repository;

import model.Memo;
import repository.factory.GlobalEntityMangerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MemoDBRepository implements MemoRepository {
    private EntityManager em;
    private static MemoDBRepository instance = new MemoDBRepository();

    private MemoDBRepository() {
    }

    public static MemoDBRepository getInstance() {
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
        em = GlobalEntityMangerFactory.getInstance().getEmf().createEntityManager();
    }
}
