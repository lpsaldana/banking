package com.sofka.account.model;

import com.sofka.account.dto.MovementDTO;
import com.sofka.account.type.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("movement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement {
    @Id
    private Long id;
    private BigDecimal amount;
    @Column("initial_balance")
    private BigDecimal initialBalance;
    @Column("movement_type")
    private MovementType movementType;
    @Column("transaction_date")
    private Instant transactionDate;
    @Column("account_id")
    private Long accountId;

    public Movement(MovementDTO movementDTO){
        this.setAmount(movementDTO.getAmount());
        this.setAccountId(movementDTO.getAccountId());
        this.setMovementType(movementDTO.getMovementType());
    }
}
