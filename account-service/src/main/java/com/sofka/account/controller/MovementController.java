package com.sofka.account.controller;

import com.sofka.account.dto.MovementDTO;
import com.sofka.account.model.Movement;
import com.sofka.account.service.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movement")
@AllArgsConstructor
public class MovementController {
    private final MovementService movementService;

    @PostMapping("/create")
    public Mono<ResponseEntity<Void>> createMovement(@RequestBody MovementDTO movementDTO){
        return movementService.createMovement(new Movement(movementDTO));
    }

    @GetMapping("/list")
    public Flux<Movement> listAllMovements(){
        return movementService.getAllMovements();
    }

    @GetMapping("/account-movements/{accountId}")
    public Flux<Movement> listAllMovements(@PathVariable("accountId") Long accountId){
        return movementService.getAllAccountMovements(accountId);
    }
}
