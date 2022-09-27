package repository;

import model.Memo;

import java.util.List;

public interface MemoRepository {
    public void save(Memo memo);
    public List<Memo> findAll();
}
