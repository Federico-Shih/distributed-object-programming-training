package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;
import ar.edu.itba.pod.grpc.GreeterGrpc.GreeterImplBase;
import io.grpc.stub.StreamObserver;

public class GreeterServant extends GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage(String.format("Hello %s", request.getName())).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
