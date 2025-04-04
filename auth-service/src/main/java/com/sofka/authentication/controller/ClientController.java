package com.sofka.authentication.controller;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.dto.ClientUpdateRequestDTO;
import com.sofka.authentication.dto.LoginDTO;
import com.sofka.authentication.model.Client;
import com.sofka.authentication.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/client")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public Mono<ResponseEntity<Void>> createClient(@RequestBody ClientDTO client){
        return clientService.createClient(client);
    }

    @GetMapping("/list")
    public Flux<Client> listAllClients(){
        return clientService.listClients();
    }

    @PostMapping("/update")
    public Mono<ResponseEntity<Void>> updateClientPassword(@RequestBody ClientUpdateRequestDTO requestDTO){
        return clientService.updateClientPassword(requestDTO);
    }

    @DeleteMapping("/delete/{clientId}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable("clientId") Long clientId){
        return clientService.deleteClient(clientId);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginDTO loginDTO){
        return clientService.loginClient(loginDTO);
    }

}
