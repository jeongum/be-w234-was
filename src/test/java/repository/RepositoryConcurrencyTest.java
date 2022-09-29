package repository;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryConcurrencyTest {

    @Test
    @DisplayName("멀티스레드 환경에서의 hashMap 동시성 테스트 실패")
    public void hashMapConcurrency() throws InterruptedException {
        HashMap<String, User> HashMapUsers = new HashMap<>();
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                User user = new User(UUID.randomUUID().toString(), "password", "name", "email");
                HashMapUsers.put(user.getUserId(), user);
                latch.countDown();
            });
        }
        latch.await();

        assertNotEquals(1000, HashMapUsers.size());
    }

    @Test
    @DisplayName("멀티스레드 환경에서의 concurrentHashMap 동시성 테스트 성공")
    public void concurrentHashMapConcurrency() throws InterruptedException {
        ConcurrentHashMap<String, User> concurrentHashMapUsers = new ConcurrentHashMap<>();
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                User user = new User(UUID.randomUUID().toString(), "password", "name", "email");
                concurrentHashMapUsers.put(user.getUserId(), user);
                latch.countDown();
            });
        }
        latch.await();

        assertEquals(1000, concurrentHashMapUsers.size());
    }
}