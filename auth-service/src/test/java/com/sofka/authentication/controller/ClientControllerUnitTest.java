package com.sofka.authentication.controller;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientControllerUnitTest {

    private final ClientService mockService = Mockito.mock(ClientService.class);
    private final ClientController controller = new ClientController(mockService);

    private final WebTestClient webTestClient = WebTestClient.bindToController(controller).build();

    @Test
    void shouldCallCreateClient() {
        when(mockService.createClient(any(ClientDTO.class)))
                .thenReturn(Mono.just(ResponseEntity.ok().build()));

        ClientDTO dto = new ClientDTO();
        dto.setUsername("mockuser");

        webTestClient.post()
                .uri("/auth/client/create")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }
}
