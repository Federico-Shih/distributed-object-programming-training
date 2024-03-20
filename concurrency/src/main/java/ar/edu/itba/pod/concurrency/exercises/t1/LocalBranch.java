package ar.edu.itba.pod.concurrency.exercises.t1;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LocalBranch {
    private static Integer AMOUNT_OF_CLIENTS = 200;
    private static Integer AMOUNT_OF_RECEPTIONIST = 2;
    private static Integer AMOUNT_OF_ATTENDANTS_HIGH = 3;
    private static Integer AMOUNT_OF_ATTENDANTS_PRIORITY = 1;
    private static Integer AMOUNT_OF_ATTENDANTS_NORMAL = 2;
    public static void main(String[] args) {
        IBranchClientQueueService queueService = new BranchClientQueueService();
        try {
            ExecutorService service = Executors.newCachedThreadPool();
            List<Callable<Integer>> works = new LinkedList<>();
            for (int i = 0; i < AMOUNT_OF_RECEPTIONIST; i += 1) {
                works.add(new Receptionist(queueService));
            }
            for (int i = 0; i < AMOUNT_OF_ATTENDANTS_HIGH; i += 1) {
                works.add(new ClientAttendant(queueService, ClientPriority.HIGH));
            }
            for (int i = 0; i < AMOUNT_OF_ATTENDANTS_NORMAL; i += 1) {
                works.add(new ClientAttendant(queueService, ClientPriority.NORMAL));
            }
            for (int i = 0; i < AMOUNT_OF_ATTENDANTS_PRIORITY; i += 1) {
                works.add(new ClientAttendant(queueService, ClientPriority.PRIORITY));
            }
            service.invokeAll(works);
            service.shutdownNow();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }
}