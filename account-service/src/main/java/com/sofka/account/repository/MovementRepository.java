package com.sofka.account.repository;

import com.sofka.account.model.Movement;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovementRepository extends R2dbcRepository<Movement,Long> {
    Flux<Movement> findAllByAccountId(Long accountId);
}
