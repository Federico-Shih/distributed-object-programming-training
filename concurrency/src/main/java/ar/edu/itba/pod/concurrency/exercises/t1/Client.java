package ar.edu.itba.pod.concurrency.exercises.t1;

public record Client(String name, ClientPriority priority) {
    @Override
    public String toString() {
        return String.format("%s %s", name, priority.name());
    }
}