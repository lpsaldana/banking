package com.sofka.authentication.repository;

import com.sofka.authentication.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
class ClientRepositoryIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    private final Client sampleClient = new Client();

    @BeforeEach
    void setUp() {
        sampleClient.setUsername("johndoe");
        sampleClient.setPassword("password123");
        sampleClient.setStatus(true);
        sampleClient.setPersonId(1001L);
    }

    @Test
    void shouldSaveAndRetrieveClientByUsername() {
        Mono<Client> operation = clientRepository.save(sampleClient)
                .then(clientRepository.getClientByUsername("johndoe"));

        StepVerifier.create(operation)
                .expectNextMatches(client ->
                        client.getUsername().equals("johndoe") &&
                                client.getPassword().equals("password123") &&
                                Boolean.TRUE.equals(client.getStatus()) &&
                                client.getPersonId().equals(1001L)
                )
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenUsernameDoesNotExist() {
        StepVerifier.create(clientRepository.getClientByUsername("nonexistent"))
                .verifyComplete();
    }
}
