package repository;

import model.Memo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoH2RepositoryTest {

    MemoRepository memoRepository = MemoH2Repository.getInstance();
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
        memoRepository.save(memo);

        // when
        List<Memo> memos = memoRepository.findAll();

        // then
        assertEquals(2, memos.size());
        assertEquals("author", memos.get(0).getAuthor());
    }

}