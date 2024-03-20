package ar.edu.itba.pod.concurrency.exercises.e1.threadSafety.coordination;

import java.util.concurrent.Semaphore;

public class LoginQueue {

    private Semaphore semaphore;

    public LoginQueue(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    void logout() {
        semaphore.release();
    }

    int availableSlots() {
        return semaphore.availablePermits();
    }
}

