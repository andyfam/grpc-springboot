package com.yufeng.springboot.grpc;

import com.yufeng.springboot.grpc.GreetRequest;
import com.yufeng.springboot.grpc.GreetResponse;
import com.yufeng.springboot.grpc.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingService extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        String name = request.getName();
        String greeting = "Hello, " + name + "!";

        GreetResponse response = GreetResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
