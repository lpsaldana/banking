package com.sofka.account.controller;

import com.sofka.account.model.Account;
import com.sofka.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/list")
    public Flux<Account> listAccounts(){
        return accountService.listAccounts();
    }

    @GetMapping("/my-accounts")
    public Flux<Account> getMyAccounts(ServerWebExchange exchange){
        return accountService.getClientAccounts(Long.parseLong(
                exchange.getRequest().getHeaders().getFirst("X-Client-Id")));
    }

    @PostMapping("/create-account")
    public Mono<ResponseEntity<Void>> create(@RequestBody Account account){
        return accountService.createAccount(account);
    }
    @PostMapping("/update-account")
    public Mono<ResponseEntity<Void>> update(@RequestBody Account account){
        return accountService.updateAccount(account);
    }
    @DeleteMapping("/delete-account/{accountId}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("accountId") Long accountId){
        return accountService.deleteAccount(accountId);
    }
    @PostMapping("/get/{accountId}")
    public Mono<Account> getAccount(@PathVariable("accountId") Long accountId){
        return accountService.getAccount(accountId);
    }
}
