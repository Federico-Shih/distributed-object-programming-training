package ar.edu.itba.pod.concurrency.exercises.e1.threadSafety.synchronize;

/**
 * Synchronized with an Object as mutex
 */
public class SynchronizdObjectVisitCounter {

    private Object lock = "lock";
    private int c = 0;

    public void addVisit() {
        synchronized (lock) {
            c++;
        }
    }

    public synchronized int getVisits() {
        synchronized (lock) {
            return c;
        }
    }

    public int peekVisits() {
        return c;
    }
}
