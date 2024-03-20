package ar.edu.itba.pod.concurrency.exercises.e3;

import ar.edu.itba.pod.concurrency.exercises.e1.GenericService;
import ar.edu.itba.pod.concurrency.exercises.e1.GenericServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecutorTest {
    private GenericService service;

    @BeforeEach
    public final void before() {
        service = GenericServiceImpl.createGenericServiceImpl();
    }

    @Test
    public final void test() {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            Future<Integer> future = executor.submit(() -> {
                for (int i = 0; i < 5; i += 1) {
                    service.addVisit();
                }
                return service.getVisitCount();
            });
            Assertions.assertEquals(5, future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
