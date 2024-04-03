package ar.edu.itba.pod.grpc.server.services;

import ar.edu.itba.pod.grpc.health.HealthServiceGrpc;

import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;

public class HealthServiceImpl extends HealthServiceGrpc.HealthServiceImplBase {
    @Override
    public void ping(Empty request, StreamObserver<StringValue> responseObserver) {
        responseObserver.onNext(StringValue.newBuilder().setValue("Pong").build());
        responseObserver.onCompleted();
    }
}
