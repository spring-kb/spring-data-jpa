package org.mine.kb.spring.data.jpa.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository} with
 * Virtual Threads.
 *
 * @author Mark Paluch
 */
@Transactional
@SpringBootTest(properties = "spring.threads.virtual.enabled=true")
@EnabledForJreRange(min = JRE.JAVA_21)
class VirtualThreadsTests {

    @Autowired
    SimpleUserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUsername("foobar");
        user.setFirstname("firstname");
        user.setLastname("lastname");
    }

    /**
     * This repository invocation runs on a dedicated virtual thread.
     */
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void supportsVirtualThreads() throws Exception {

        BlockingQueue<String> thread = new LinkedBlockingQueue<>();
        repository.save(new User("Customer1", "Foo"));
        repository.save(new User("Customer2", "Bar"));

        try (SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor()) {
            executor.setVirtualThreads(true);

            var future = executor.submit(() -> {
                thread.add(Thread.currentThread().toString());
                return repository.findAll();
            });

            List<User> users = future.get();
            String threadName = thread.poll(1, TimeUnit.SECONDS);

            assertThat(threadName).contains("VirtualThread");
            assertThat(users).hasSize(2);
        }

        repository.deleteAll();
    }

    /**
     * Here we demonstrate the usage of {@link CompletableFuture} as a result
     * wrapper for asynchronous repository query
     * methods running on Virtual Threads. Note, that we need to disable the
     * surrounding transaction to be able to
     * asynchronously read the written data from another thread within the same test
     * method.
     */
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void asyncUsesVirtualThreads() throws Exception {

        BlockingQueue<String> thread = new LinkedBlockingQueue<>();
        repository.save(new User("Customer1", "Foo"));
        repository.save(new User("Customer2", "Bar"));

        var future = repository.readAllBy().thenAccept(users -> {

            assertThat(users).hasSize(2);
            thread.add(Thread.currentThread().toString());
        });

        future.join();
        String threadName = thread.poll(1, TimeUnit.SECONDS);

        assertThat(threadName).contains("VirtualThread");

        repository.deleteAll();
    }
}