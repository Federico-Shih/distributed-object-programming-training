package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.*;

/**
 * Basic implementation of {@link GenericService}.
 */
public  class GenericServiceImpl implements GenericService {
    int counter = 0;
    Deque<String> list = new LinkedList<>();

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
        this.counter += 1;
    }

    @Override
    public int getVisitCount() {
        return counter;
    }

    @Override
    public boolean isServiceQueueEmpty() {
        return list.isEmpty();
    }

    @Override
    public void addToServiceQueue(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        list.add(name);
    }

    @Override
    public String getFirstInServiceQueue() {
        return Optional.ofNullable(list.poll()).orElseThrow(() ->  new IllegalStateException("No one in queue"));
    }
}
