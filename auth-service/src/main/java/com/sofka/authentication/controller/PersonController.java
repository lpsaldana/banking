package com.sofka.authentication.controller;

import com.sofka.authentication.model.Person;
import com.sofka.authentication.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/auth/person")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/create")
    public Mono<ResponseEntity<Person>> createPerson(@RequestBody Person person){
        return personService.createPerson(person)
                .map(savedPerson -> ResponseEntity.status(HttpStatus.CREATED).body(savedPerson));
    }

    @GetMapping("/get-person/{personId}")
    public Mono<Person> getPerson(@PathVariable("personId") Long personId){
        return personService.getPersonById(personId);
    }

    @GetMapping("/list")
    public Flux<Person> getPeopleList(){
        return personService.listPeople();
    }

    @PostMapping("/update")
    public Mono<ResponseEntity<Void>> updatePerson(@RequestBody Person person){
        return personService.updatePerson(person);
    }

    @DeleteMapping("/delete/{personId}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable("personId") Long personId){
        return personService.deletePerson(personId);
    }

    @GetMapping("{personId}")
    public Mono<Person> getById(@PathVariable("personId") Long personId){
        return personService.getPersonById(personId);
    }

}
