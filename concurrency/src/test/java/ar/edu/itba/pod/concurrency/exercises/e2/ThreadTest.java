package ar.edu.itba.pod.concurrency.exercises.e2;

import ar.edu.itba.pod.concurrency.exercises.e1.GenericService;
import ar.edu.itba.pod.concurrency.exercises.e1.GenericServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThreadTest {
    private GenericService service;

    @BeforeEach
    public final void before() {
        service = GenericServiceImpl.createGenericServiceImpl();
    }

    @Test
    public final void testRawThread() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i += 1) {
                    service.addVisit();
                }
                System.out.println(service.getVisitCount());
            }
        };
        t1.start();
         try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testRunnable() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i += 1) {
                    service.addVisit();
                }
                System.out.println(service.getVisitCount());
            }
        };
        Thread t1 = new Thread(r1);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testLambda() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i += 1) {
                service.addVisit();
            }
            System.out.println(service.getVisitCount());
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
