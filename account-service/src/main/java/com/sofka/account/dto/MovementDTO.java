package com.sofka.account.dto;

import com.sofka.account.type.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
    private MovementType movementType;
    private Long accountId;
    private BigDecimal amount;
}
