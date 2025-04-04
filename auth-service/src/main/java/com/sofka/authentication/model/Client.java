package com.sofka.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("client")
public class Client {
    @Id
    private Long id;
    private String username;
    private String password;
    private Boolean status;
    @Column("person_id")
    private Long personId;
}
