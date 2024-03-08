package ar.edu.itba.pod.concurrency.exercises.t1;

import java.util.concurrent.Callable;

public class Receptionist implements Callable<Integer > {
    private static final Integer AMOUNT_OF_CLIENTS = 100;
    private final IBranchClientQueueService clientService;
    public Receptionist(final IBranchClientQueueService clientService) {
        this.clientService = clientService;
    }
    @Override
    public Integer call() throws Exception {
        // simulate one client and enqueue
// sleep for a couple of random seconds.
        for (int i = 0; i < AMOUNT_OF_CLIENTS; i++) {
            ClientPriority priority = switch (i % 3) {
                case 0 -> ClientPriority.NORMAL;
                case 1 -> ClientPriority.HIGH;
                default -> ClientPriority.PRIORITY;
            };
            Client client = new Client(String.format("%d", i), priority);
            System.out.printf("RECEPTIONIST - Submitting client %s\n", client);
            clientService.receiveClient(client);
            Thread.sleep((long) (2000 * Math.random()));
        }
        return AMOUNT_OF_CLIENTS;
    }
}