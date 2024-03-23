package ar.edu.itba.pod.concurrency.iii.pubsub;

import java.util.concurrent.BlockingQueue;

public class NumbersConsumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int poisonPill;
    private int sum = 0;

    public NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }

    // TODO: Modify so it sums the items up at the end.
    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                if (number.equals(poisonPill)) {
                    System.out.println(Thread.currentThread().getName() + " result: " + sum);
                    return;
                }
                sum += number;
//                System.out.println(Thread.currentThread().getName() + " result: " + number);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}