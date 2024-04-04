package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.UtilsServiceGrpc;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    private static UtilsServiceGrpc.UtilsServiceBlockingStub stub;

    public static void main(String[] args) throws InterruptedException {
        logger.info("grpc-utils Client Starting ...");
        logger.info("grpc-com-patterns Client Starting ...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = UtilsServiceGrpc.newBlockingStub(channel);

        try {
            boolean running = true;
            while (running) {
                Scanner reader = new Scanner(System.in);
                System.out.println("Enter command: ping, hello, echo, time, fortune, exit");
                String command = reader.nextLine();
                switch (command) {
                    case "ping" -> {
                        var result = stub.ping(Empty.getDefaultInstance());
                        System.out.println(result.getValue());
                    }
                    case "hello" -> {
                        System.out.print("Your name: ");
                        String text = reader.nextLine();
                        var result = stub.hello(StringValue.of(text));
                        System.out.println(result.getValue());
                    }
                    // Sorry dont want to implement string splitting for echo and hello fuck you
                    case "echo" -> {
                        String text = reader.nextLine();
                        var result = stub.echo(StringValue.of(text));
                        System.out.println(result.getValue());
                    }
                    case "time" -> {
                        var result = stub.time(Empty.getDefaultInstance());
                        System.out.println(LocalDateTime.ofEpochSecond(result.getValue(), 0, ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now())));;
                    }
                    case "fortune" -> {
                        var result = stub.fortune(Empty.getDefaultInstance());
                        System.out.println(result.getValue());
                    }
                    case "exit" -> {
                        running = false;
                    }
                }
            }
        } finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }
}
