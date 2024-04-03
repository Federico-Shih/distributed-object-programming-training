package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.user.LoginInformation;
import ar.edu.itba.pod.grpc.user.User;
import ar.edu.itba.pod.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("grpc-com-patterns Client Starting ...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build();
//        HealthServiceGrpc.HealthServiceBlockingStub stub = HealthServiceGrpc.newBlockingStub(channel);
//        var us = UserServiceGrpc.newFutureStub(channel);
        var us = UserServiceGrpc.newStub(channel);
        try {
            System.out.println("Logging in...");
            us.doLogin(
                LoginInformation.newBuilder().setUserName("a").setPassword("chau").build(),
                new StreamObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        System.out.println(user);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println(throwable);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("hola");
                    }
                }
            );
//            Futures.addCallback(us.doLogin(
//                    LoginInformation.newBuilder().setUserName("hola").setPassword("chau").build()
//                ),
//                new FutureCallback<>() {
//                    @Override
//                    public void onSuccess(User user) {
//                        System.out.println(user);
//                        System.out.println("Getting roles...");
//
//                        try {
//                            System.out.println(us.getRoles(user).get());
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        } catch (ExecutionException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        System.out.println(throwable);
//                    }
//                },
//                executor
//            );

        } finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
            channel.shutdownNow();
        }
    }
}
