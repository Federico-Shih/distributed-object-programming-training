package ar.edu.itba.pod.grpc.server.services;

import ar.edu.itba.pod.grpc.user.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private final Map<Pair<String, String>, User> users;
    private final Map<Integer, Map<String, Role>> roles;

    public UserServiceImpl() {
        this.users = new HashMap<>();
        this.users.put(
            new Pair<>("hola", "chau"),
            User.newBuilder()
                .setId(1)
                .setStatus(AccountStatus.ACCOUNT_STATUS_ACTIVE)
                .setUserName("hola")
                .setDisplayName("hola chua")
                .addPreferences("banana")
                .build()
        );
        this.roles = new HashMap<>();
        var role = new HashMap<String, Role>();
        role.put("hola", Role.ADMIN);
        this.roles.put(1, role);
    }

    @Override
    public void doLogin(LoginInformation request, StreamObserver<User> responseObserver) {
        Pair<String, String> creds = new Pair<>(request.getUserName(), request.getPassword());
        if (!this.users.containsKey(creds)) {
            responseObserver.onError(new StatusException(Status.INVALID_ARGUMENT));
            return;
        }
        responseObserver.onNext(this.users.get(creds));
        responseObserver.onCompleted();
    }

    @Override
    public void getRoles(User request, StreamObserver<UserRoles> responseObserver) {
        if (!this.roles.containsKey(request.getId())) {
            responseObserver.onError(new StatusException(Status.FAILED_PRECONDITION));
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onNext(UserRoles.newBuilder().putAllRolesBySite(this.roles.get(request.getId())).build());
        responseObserver.onCompleted();
    }
}
