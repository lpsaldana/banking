package com.sofka.authentication.service;

import com.sofka.authentication.model.Person;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<Person> createPerson(Person client);
    Mono<ResponseEntity<Void>> updatePerson(Person client);
    Mono<ResponseEntity<Void>> deletePerson(Long id);
    Flux<Person> listPeople();

    Mono<Person> getPersonById(Long id);
}
