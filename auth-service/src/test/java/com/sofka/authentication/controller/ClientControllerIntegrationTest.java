package com.sofka.authentication.controller;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.model.Client;
import com.sofka.authentication.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class ClientControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() {
        clientRepository.deleteAll().block(); // limpia antes de cada test
    }

    @Test
    void shouldCreateClient() {
        ClientDTO dto = new ClientDTO("testuser", "12345", 1L);

        webTestClient.post()
                .uri("/auth/client/create")
                .body(Mono.just(dto), ClientDTO.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldListClients() {
        Client client = new Client(null, "user", "pass", true, 1L);
        clientRepository.save(client).block();

        webTestClient.get()
                .uri("/auth/client/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Client.class)
                .hasSize(1);
    }
}
