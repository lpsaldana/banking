package com.sofka.account.model;

import com.sofka.account.type.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("account")
public class Account {
    @Id
    private Long id;
    @Column("account_number")
    private String accountNumber;
    @Column("account_type")
    private AccountType accountType;
    private BigDecimal balance;
    private Boolean status;
    private Long clientId;

}

