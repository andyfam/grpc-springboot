package com.yufeng.springboot.grpc;

import com.yufeng.springboot.grpc.GreetRequest;
import com.yufeng.springboot.grpc.GreetResponse;
import com.yufeng.springboot.grpc.GreetServiceGrpc;
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
@SpringJUnitConfig(classes = { GreetingServiceIntegrationTestConfiguration.class })
// Spring doesn't start without a config (might be empty)
@DirtiesContext // Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
public class GreetingServiceTest {
    @GrpcClient("inProcess")
    private GreetServiceGrpc.GreetServiceBlockingStub greetServiceBlockingStub;

    @Test
    @DirtiesContext
    void greet_shouldReturnGreeting() {
        // Arrange
        String name = "World";

        // Act
        GreetResponse response = greetServiceBlockingStub.greet(
                GreetRequest.newBuilder().setName(name).build());

        // Assert
        assertNotNull(response);
        assertEquals("Hello, World!", response.getGreeting());
    }
}
