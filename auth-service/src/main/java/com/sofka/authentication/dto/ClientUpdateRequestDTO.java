package com.sofka.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientUpdateRequestDTO {
    Long id;
    String password;
}
