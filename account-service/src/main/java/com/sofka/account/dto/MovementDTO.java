package com.sofka.account.dto;

import com.sofka.account.type.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MovementDTO {
    private MovementType movementType;
    private Long accountId;
    private BigDecimal amount;
}
