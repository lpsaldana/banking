package com.sofka.account.repository;

import com.sofka.account.model.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends R2dbcRepository<Account, Long> {
    Flux<Account> findAllByClientId(Long clientId);
}
