package ar.edu.itba.pod.concurrency.exercises.t1;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BranchClientQueueService implements IBranchClientQueueService {
    private final Deque<Client> highPriorityQueue;
    private final Deque<Client> normalPriorityQueue;
    private final Deque<Client> priorityQueue;

    public BranchClientQueueService() {
        this.highPriorityQueue = new ConcurrentLinkedDeque<>();
        this.normalPriorityQueue = new ConcurrentLinkedDeque<>();
        this.priorityQueue = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void receiveClient(Client client) {
        switch (client.priority()) {
            case HIGH -> highPriorityQueue.add(client);
            case NORMAL -> normalPriorityQueue.add(client);
            case PRIORITY -> priorityQueue.add(client);
        }
    }

    @Override
    public Client clientForPriority(ClientPriority priority) {
        switch (priority) {
            case HIGH -> {
                return highPriorityQueue.poll();
            }
            case NORMAL -> {
                return normalPriorityQueue.poll();
            }
            case PRIORITY -> {
                return priorityQueue.poll();
            }
        }
        return null;
    }
}
