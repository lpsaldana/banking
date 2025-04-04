package com.sofka.authentication.service;

import com.sofka.authentication.exceptions.DataNotFoundException;
import com.sofka.authentication.exceptions.DniDuplicatedException;
import com.sofka.authentication.model.Person;
import com.sofka.authentication.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    @Override
    public Mono<Person> createPerson(Person person) {
        return personRepository.getPersonByDni(person.getDni())
                .flatMap(person1 -> Mono.error(new DniDuplicatedException()))
                .switchIfEmpty(personRepository.save(person))
                .cast(Person.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> updatePerson(Person person) {
        return personRepository.findById(person.getId())
                .flatMap(person1 -> personRepository.save(person))
                .switchIfEmpty(Mono.error(new DataNotFoundException(person.getId())))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deletePerson(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(id)))
                .flatMap(personRepository::delete)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Flux<Person> listPeople() {
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> getPersonById(Long id){
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(id)));
    }
}
