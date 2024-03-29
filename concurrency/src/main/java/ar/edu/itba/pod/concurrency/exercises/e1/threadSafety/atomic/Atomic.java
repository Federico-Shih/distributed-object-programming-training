package ar.edu.itba.pod.concurrency.exercises.e1.threadSafety.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link Counter} with {@link AtomicLong}
 */
public class Atomic implements Counter {
    private final AtomicLong atomic = new AtomicLong();

    public long getCounter() {
        return atomic.get();
    }

    public void increment() {
        atomic.incrementAndGet();
    }
}
