package com.sofka.account.service;

import com.sofka.account.model.Account;
import com.sofka.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Flux<Account> getClientAccounts(Long clientId) {
        return accountRepository.findAllByClientId(clientId);
    }

    @Override
    public Mono<ResponseEntity<Void>> createAccount(Account account) {
        return accountRepository.save(account)
                .flatMap(acc -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> updateAccount(Account account) {
        account.setStatus(true);
        return accountRepository.save(account)
                .flatMap(acc -> Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")))
                .flatMap(accountRepository::delete)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Mono<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
