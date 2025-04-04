package com.sofka.account.service;

import com.sofka.account.model.Account;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> listAccounts();
    Flux<Account> getClientAccounts(Long clientId);
    Mono<ResponseEntity<Void>> createAccount(Account account);
    Mono<ResponseEntity<Void>> updateAccount(Account account);
    Mono<ResponseEntity<Void>> deleteAccount(Long accountId);
    Mono<Account> getAccount(Long accountId);

}
