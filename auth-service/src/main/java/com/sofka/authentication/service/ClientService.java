package com.sofka.authentication.service;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.dto.ClientUpdateRequestDTO;
import com.sofka.authentication.dto.LoginDTO;
import com.sofka.authentication.model.Client;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<ResponseEntity<Void>> createClient(ClientDTO client);
    Mono<ResponseEntity<Void>> updateClientPassword(ClientUpdateRequestDTO client);
    Mono<ResponseEntity<Void>> deleteClient(Long id);
    Flux<Client> listClients();
    Mono<String> loginClient(LoginDTO loginDTO);

}
