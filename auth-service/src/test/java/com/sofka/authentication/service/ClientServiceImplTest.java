package com.sofka.authentication.service;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.dto.ClientUpdateRequestDTO;
import com.sofka.authentication.dto.LoginDTO;
import com.sofka.authentication.exceptions.DataNotFoundException;
import com.sofka.authentication.exceptions.InvalidCredentialsException;
import com.sofka.authentication.exceptions.UsernameTakenException;
import com.sofka.authentication.model.Client;
import com.sofka.authentication.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client(1L, "testUser", "encodedPassword", true, 100L);
    }

    @Test
    void createClient_Success() {
        ClientDTO clientDTO = new ClientDTO("newUser", "password", 1L);
        when(clientRepository.getClientByUsername(clientDTO.getUsername())).thenReturn(Mono.empty());
        when(passwordEncoder.encode(clientDTO.getPassword())).thenReturn("encodedPassword");
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(testClient));

        StepVerifier.create(clientService.createClient(clientDTO))
                .expectNext(ResponseEntity.status(201).build())
                .verifyComplete();

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void createClient_UsernameTaken() {
        ClientDTO clientDTO = new ClientDTO("newUser", "password", 1L);
        when(clientRepository.getClientByUsername(clientDTO.getUsername())).thenReturn(Mono.just(testClient));
        when(passwordEncoder.encode(clientDTO.getPassword())).thenReturn("encodedPassword");
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(testClient));

        StepVerifier.create(clientService.createClient(clientDTO))
                .expectError(UsernameTakenException.class)
                .verify();
    }

    @Test
    void updateClientPassword_Success() {
        ClientUpdateRequestDTO updateRequest = new ClientUpdateRequestDTO(1L, "newPassword");
        when(clientRepository.findById(1L)).thenReturn(Mono.just(testClient));
        when(passwordEncoder.encode(updateRequest.getPassword())).thenReturn("newEncodedPassword");
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(testClient));

        StepVerifier.create(clientService.updateClientPassword(updateRequest))
                .expectNext(ResponseEntity.status(200).build())
                .verifyComplete();
    }

    @Test
    void updateClientPassword_NotFound() {
        ClientUpdateRequestDTO updateRequest = new ClientUpdateRequestDTO(1L, "newPassword");
        when(clientRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.updateClientPassword(updateRequest))
                .expectError(DataNotFoundException.class)
                .verify();
    }

    @Test
    void deleteClient_Success() {
        when(clientRepository.findById(1L)).thenReturn(Mono.just(testClient));
        when(clientRepository.delete(testClient)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClient(1L))
                .expectNext(ResponseEntity.status(200).build())
                .verifyComplete();
    }

    @Test
    void deleteClient_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClient(1L))
                .expectError(DataNotFoundException.class)
                .verify();
    }

    @Test
    void listClients_Success() {
        when(clientRepository.findAll()).thenReturn(Flux.just(testClient));

        StepVerifier.create(clientService.listClients())
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void loginClient_InvalidCredentials() {
        LoginDTO loginDTO = new LoginDTO("testUser", "wrongPassword");
        when(clientRepository.getClientByUsername(loginDTO.getUsername())).thenReturn(Mono.just(testClient));
        when(passwordEncoder.matches(loginDTO.getPassword(), testClient.getPassword())).thenReturn(false);

        StepVerifier.create(clientService.loginClient(loginDTO))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }
}
