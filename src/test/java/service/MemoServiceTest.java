package service;

import exception.MemoException;
import model.Memo;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.MemoDBRepository;
import repository.MemoRepository;
import repository.UserDBRepository;
import repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoServiceTest {

    private MemoService memoService = MemoService.getInstance();
    private UserRepository userRepository = UserDBRepository.getInstance();
    private MemoRepository memoRepository = MemoDBRepository.getInstance();

    @Test
    @DisplayName("userId에 매핑되는 유저를 불러와 메모를 생성한다.")
    void create() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("contents", "contents");
        User user = User.builder().userId("userId").name("name").build();
        userRepository.save(user);

        // when
        Memo memo = memoService.create("userId", body);

        // then
        assertNotNull(memo.getId());
        assertNotNull(memo.getCreateDate());
        assertEquals(user.getName(), memo.getAuthor());
        assertNotNull("contents", memo.getContents());
    }

    @Test
    @DisplayName("Invalid Parameter로 메모 생성에 실패한다.")
    void createWithException() {
        // given
        Map<String, String> body = new HashMap<>();
        User user = User.builder().userId("userId").name("name").build();
        userRepository.save(user);

        // when
        // then
        assertThrows(MemoException.class, () -> memoService.create("userId", body));
    }

    @Test
    void list() {
        // given
        int originSize = memoRepository.findAll().size();
        memoRepository.save(Memo.builder().contents("HI").build());

        // when
        List<Memo> memos = memoService.list();

        // then
        assertEquals(originSize + 1, memos.size());
        assertEquals("HI", memos.get(memos.size() - 1).getContents());
    }
}