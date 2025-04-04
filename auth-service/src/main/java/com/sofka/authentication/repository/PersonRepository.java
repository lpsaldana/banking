package com.sofka.authentication.repository;

import com.sofka.authentication.model.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<Person, Long> {
    Mono<Person> getPersonByDni(String dni);
}
