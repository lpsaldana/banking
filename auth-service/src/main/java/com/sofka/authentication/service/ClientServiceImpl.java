package com.sofka.authentication.service;

import com.sofka.authentication.dto.ClientDTO;
import com.sofka.authentication.dto.ClientUpdateRequestDTO;
import com.sofka.authentication.dto.LoginDTO;
import com.sofka.authentication.exceptions.DataNotFoundException;
import com.sofka.authentication.exceptions.InvalidCredentialsException;
import com.sofka.authentication.exceptions.UsernameTakenException;
import com.sofka.authentication.model.Client;
import com.sofka.authentication.repository.ClientRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<ResponseEntity<Void>> createClient(ClientDTO client) {
        return clientRepository.getClientByUsername(client.getUsername())
                .flatMap(client1 -> Mono.error(new UsernameTakenException()))
                .switchIfEmpty(clientRepository.save(
                        new Client(null,
                                client.getUsername(),
                                passwordEncoder.encode(client.getPassword()),
                                Boolean.TRUE,
                                client.getPersonId())))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> updateClientPassword(ClientUpdateRequestDTO client) {
        return clientRepository.findById(client.getId())
                .flatMap(client1 -> {
                    client1.setPassword(passwordEncoder.encode(client.getPassword()));
                    return clientRepository.save(client1);
                })
                .switchIfEmpty(Mono.error(new DataNotFoundException(client.getId())))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteClient(Long id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(id)))
                .flatMap(clientRepository::delete)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }

    @Override
    public Flux<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<String> loginClient(LoginDTO loginDTO) {
        return clientRepository.getClientByUsername(loginDTO.getUsername())
                .filter(client -> passwordEncoder.matches(loginDTO.getPassword(), client.getPassword()))
                .map(this::generateJwtToken)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException()));
    }

    private String generateJwtToken(Client client){
        return Jwts.builder()
                .setSubject(client.getId().toString())
                .claim("clientId", client.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
