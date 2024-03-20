package ar.edu.itba.pod.concurrency.exercises.e1.threadSafety.atomic;

/**
 * Counter interface.
 */
public interface Counter {
    long getCounter();

    void increment();
}
