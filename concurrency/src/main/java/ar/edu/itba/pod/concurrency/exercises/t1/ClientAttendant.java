package ar.edu.itba.pod.concurrency.exercises.t1;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

// imports
public class ClientAttendant implements Callable<Integer > {
    private final IBranchClientQueueService clientService;
    private final ClientPriority priority;
    private final static int MAX_MILLIS_ATTENDING = 1500;
    private final static int MAX_MILLIS_WASTING = 3000;
    public ClientAttendant(IBranchClientQueueService clientService,
                           ClientPriority priority) {
        this.clientService = clientService;
        this.priority = priority;
    }
    @Override
    public Integer call() throws Exception {
        boolean stillWorking = true;
        int cycles = 0;
        int clientProcessed = 0;
        while (stillWorking) {
            //if 3 cycles with no client end.
//get one client and sleep for random amount of seconds t simulate service time
// or if no client sleep to simulate waiting time.
            Client client = this.clientService.clientForPriority(priority);
            if (client == null && cycles == 3) {
                stillWorking = false;
            } else if (client == null) {
                Thread.sleep(MAX_MILLIS_WASTING);
                cycles += 1;
            } else {
                cycles = 0;
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.printf("ATTENDANT - Processing client: %s - Priority %s - %s\n", client.name(), client.priority().name(), timestamp);

                Thread.sleep((long) (Math.random() * MAX_MILLIS_ATTENDING));
                System.out.printf("ATTENDANT - Finished client: %s - Priority %s - %s\n", client.name(), client.priority().name(), timestamp);

                clientProcessed += 1;
            }
        }
        System.out.printf("Finished processing clients: %d\n", clientProcessed);
        return clientProcessed; // how many clients
    }
}