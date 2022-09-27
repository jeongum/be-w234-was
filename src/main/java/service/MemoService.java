package service;

import model.Memo;
import repository.MemoDBRepository;
import repository.MemoRepository;

import java.util.List;

public class MemoService {
    private MemoRepository memoRepository;
    private static MemoService instance = new MemoService();

    private MemoService() {
        memoRepository = MemoDBRepository.getInstance();
    }

    public static MemoService getInstance() {
        return instance;
    }

    public List<Memo> list() {
        return memoRepository.findAll();
    }
}
