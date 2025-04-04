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
@Table("person")
public class Person {
    @Id
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String dni;
    private String address;
    @Column("phone_number")
    private String phoneNumber;

}
