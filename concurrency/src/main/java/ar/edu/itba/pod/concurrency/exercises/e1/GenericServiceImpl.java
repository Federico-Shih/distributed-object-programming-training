package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic implementation of {@link GenericService}.
 */
public  class GenericServiceImpl implements GenericService {
    final Object lock = "LOCK VISITS";
    int counter;
    final Deque<String> list;

    private GenericServiceImpl() {
        counter = 0;
        list = new LinkedList<>();
    }

    public static GenericService createGenericServiceImpl() {
        return new GenericServiceImpl();
    }


    @Override
    public String echo(String message) {
        return message;
    }

    @Override
    public String toUpper(String message) {
        return Optional.ofNullable(message).map(String::toUpperCase).orElse(null);
    }

    @Override
    public void addVisit() {
        synchronized (lock) {
            this.counter += 1;
        }
    }

    @Override
    public int getVisitCount() {
        synchronized (lock) {
            return counter;
        }
    }

    @Override
    public boolean isServiceQueueEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    @Override
    public void addToServiceQueue(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        synchronized (list) {
            list.add(name);
        }
    }

    @Override
    public String getFirstInServiceQueue() {
        synchronized (list) {
            return Optional.ofNullable(list.poll()).orElseThrow(() ->  new IllegalStateException("No one in queue"));
        }
    }
}
