package repository;

import model.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {
    Memo save(Memo memo);

    List<Memo> findAll();
}
