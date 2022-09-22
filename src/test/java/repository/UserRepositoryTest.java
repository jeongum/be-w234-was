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

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {

    private UserRepository userRepository = UserMemoryRepository.getInstance();

    @Test
    @DisplayName("멀티스레드 환경에서의 repository field 동시성 테스트")
    public void hashMapConcurrency() throws InterruptedException {
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                userRepository.save(new User(UUID.randomUUID().toString(), "password", "name", "email"));
                latch.countDown();
            });
        }
        latch.await();

        assertEquals(1000, userRepository.count());
    }

}