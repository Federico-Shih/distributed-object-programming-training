package ar.edu.itba.pod.grpc.server.services;

import ar.edu.itba.pod.grpc.UtilsServiceGrpc;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import com.google.protobuf.UInt64Value;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

public class UtilsServiceImpl extends UtilsServiceGrpc.UtilsServiceImplBase {
    private final List<String> fortunes;

    public UtilsServiceImpl(List<String> fortunes) {
        this.fortunes = fortunes;
    }

    @Override
    public void ping(Empty request, StreamObserver<StringValue> responseObserver) {
        responseObserver.onNext(StringValue.newBuilder().setValue("Pong").build());
        responseObserver.onCompleted();
    }

    @Override
    public void time(Empty request, StreamObserver<UInt64Value> responseObserver) {
        responseObserver.onNext(UInt64Value.of(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()));
        responseObserver.onCompleted();
    }

    @Override
    public void echo(StringValue request, StreamObserver<StringValue> responseObserver) {
        responseObserver.onNext(StringValue.of(request.getValue()));
        responseObserver.onCompleted();
    }

    @Override
    public void hello(StringValue request, StreamObserver<StringValue> responseObserver) {
        // Consider what happens when request is longer
        responseObserver.onNext(StringValue.of(String.format("Hello %s", request.getValue())));
        responseObserver.onCompleted();
    }

    @Override
    public void fortune(Empty request, StreamObserver<StringValue> responseObserver) {
        if (fortunes.isEmpty()) {
            responseObserver.onError(new StatusException(Status.INTERNAL));
            return;
        }
        int index = (int) Math.floor(Math.random() * fortunes.size());
        responseObserver.onNext(StringValue.of(fortunes.get(index)));
        responseObserver.onCompleted();
    }
}
