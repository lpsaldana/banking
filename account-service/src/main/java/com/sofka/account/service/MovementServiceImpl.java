package com.sofka.account.service;

import com.sofka.account.model.Movement;
import com.sofka.account.repository.AccountRepository;
import com.sofka.account.repository.MovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService{
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Override
    public Flux<Movement> getAllAccountMovements(Long accountId) {
        return movementRepository.findAllByAccountId(accountId);
    }

    @Override
    public Flux<Movement> getAllMovements() {
        return movementRepository.findAll();
    }


    @Override
    public Mono<ResponseEntity<Void>> createMovement(Movement movement) {
        return accountRepository.findById(movement.getAccountId())
                .flatMap(account -> {
                    movement.setInitialBalance(account.getBalance());
                    movement.setTransactionDate(Instant.now());
                    switch (movement.getMovementType()){
                        case RETIRO -> {
                            if(movement.getInitialBalance().doubleValue() >= movement.getAmount().doubleValue()){
                                double newAmount = movement.getInitialBalance().doubleValue() -
                                        movement.getAmount().doubleValue();
                                account.setBalance(BigDecimal.valueOf(newAmount));
                            }else{
                                return Mono.error(
                                        new RuntimeException("No hay fondos suficientes para realizar esta transacción"));
                            }
                        }
                        case DEPOSITO -> {
                            double newAmount = movement.getInitialBalance().doubleValue() +
                                    movement.getAmount().doubleValue();
                            account.setBalance(BigDecimal.valueOf(newAmount));
                        }
                        default -> {
                            return Mono.error(
                                    new RuntimeException("Operación no implementada"));
                        }
                    }
                    return accountRepository.save(account);
                }).flatMap(account -> movementRepository.save(movement))
                .flatMap(movement1 -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }
}
