package service;

import exception.MemoException;
import exception.MemoExceptionMessage;
import model.Memo;
import model.User;
import repository.MemoDBRepository;
import repository.MemoRepository;
import repository.UserDBRepository;
import repository.UserRepository;
import webserver.handler.dto.MemoCreateDto;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MemoService {
    private MemoRepository memoRepository;
    private UserRepository userRepository;
    private static MemoService instance = new MemoService();

    private MemoService() {
        memoRepository = MemoDBRepository.getInstance();
        userRepository = UserDBRepository.getInstance();
    }

    public static MemoService getInstance() {
        return instance;
    }

    public Memo create(String userId, MemoCreateDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MemoException(MemoExceptionMessage.MEMO_CREATE_ERROR));
        Memo memo = Memo.builder().author(user.getName()).contents(dto.getContents()).build();

        memoRepository.save(memo);

        return memo;
    }

    public List<Memo> list() {
        return memoRepository.findAll();
    }
}
