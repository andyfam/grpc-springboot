package com.yufeng.springboot.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the inProcess server
})
// Spring doesn't start without a config (might be empty)
@SpringJUnitConfig(classes = { GreetingServiceIntegrationTestConfiguration.class })
@DirtiesContext
public class GreetingServiceTest {
    @GrpcClient("inProcess")
    private GreetServiceGrpc.GreetServiceBlockingStub greetServiceBlockingStub;

    @Test
    @DirtiesContext
    void greet_shouldReturnGreeting() {
        String name = "World";

        GreetResponse response = greetServiceBlockingStub.greet(
                GreetRequest.newBuilder().setName(name).build());

        assertNotNull(response);
        assertEquals("Hello, World!", response.getGreeting());
    }
}
