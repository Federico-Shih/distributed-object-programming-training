package ar.edu.itba.pod.concurrency.exercises.e1_threadsafety;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ar.edu.itba.pod.concurrency.exercises.e1.GenericService;
import ar.edu.itba.pod.concurrency.exercises.e1.GenericServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link GenericService} using {@link Thread}s
 */
public class GenericServiceConcurrencyTest {
    private static final int VISITS_BY_THREAD = 1000;
    private static final int THREAD_COUNT = 10;
    private static final int EXPECTED_VISITS = VISITS_BY_THREAD * THREAD_COUNT;

    private GenericService service;

    @BeforeEach
    public final void before() {
        service = GenericServiceImpl.createGenericServiceImpl();
    }

    /**
     * Makes VISITS_BY_THREAD visits to the service
     */
    private final Runnable visitor = () -> {
        for (int i = 0; i < VISITS_BY_THREAD; i += 1) {
            service.addVisit();
        }
    };

    private ExecutorService pool;

    /**
     * generates THREAD_COUNT threads with {@link #visitor} and runs them.
     */
    @Test
    public final void visit_count_with_thread_start() throws InterruptedException {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i += 1) {
            threads[i] = new Thread(visitor);
            threads[i].start();
        }

        for (int i = 0; i < THREAD_COUNT; i += 1) {
            threads[i].join();
        }
        assertEquals(EXPECTED_VISITS, service.getVisitCount());
    }

    /**
     * generates THREAD_COUNT threads with {@link #visitor} and runs them submiting it via
     * the {@link ExecutorService}
     */
    @Test
    public final void visit_count_with_executor_submit() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Callable<Void>> futureList = IntStream.range(0, THREAD_COUNT).mapToObj((i) -> toCallable(visitor)).collect(Collectors.toList());
        pool.invokeAll(futureList);
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
        assertEquals(EXPECTED_VISITS, service.getVisitCount());
    }

    private Callable<Void> toCallable(final Runnable runnable) {
        return () -> {
            runnable.run();
            return null;
        };
    }
}