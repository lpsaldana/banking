package com.sofka.account.service;

import com.sofka.account.model.Account;
import com.sofka.account.model.Movement;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<Movement> getAllAccountMovements(Long accountId);
    Flux<Movement> getAllMovements();
    Mono<ResponseEntity<Void>> createMovement(Movement movement);

}
