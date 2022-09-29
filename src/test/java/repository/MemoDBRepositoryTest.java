package repository;

import model.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoDBRepositoryTest {

    MemoRepository memoRepository = MemoDBRepository.getInstance();
    Memo memo;

    @BeforeEach
    void setUp() {
        memo = Memo.builder().author("author").contents("contents").build();
    }

    @Test
    void save() {
        // given
        // when
        Memo savedMemo = memoRepository.save(memo);

        // then
        assertNotNull(savedMemo.getId());
        assertEquals("author", savedMemo.getAuthor());
        assertEquals("contents", savedMemo.getContents());
        assertNotNull(savedMemo.getCreateDate());
    }

    @Test
    void findAll() {
        // given
        int originSize = memoRepository.findAll().size();
        memoRepository.save(memo);

        // when
        List<Memo> memos = memoRepository.findAll();

        // then
        assertEquals(originSize + 1, memos.size());
        assertEquals("author", memos.get(memos.size() - 1).getAuthor());
    }

}