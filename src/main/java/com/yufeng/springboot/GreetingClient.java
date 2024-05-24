package com.yufeng.springboot;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingClient {
    @GrpcClient("hello")
    GreetServiceGrpc.GreetServiceBlockingStub stub;

    @GetMapping("/greet/{name}")
    String index(@PathVariable String name){
        GreetRequest request = GreetRequest.newBuilder().setName(name).build();

        GreetResponse resonse = stub.greet(request);

        return resonse.getGreeting();
    }
}
