package ar.edu.itba.pod.concurrency.exercises.t1;

public interface IBranchClientQueueService {
    void receiveClient(Client client);
    Client clientForPriority(ClientPriority priority);
}