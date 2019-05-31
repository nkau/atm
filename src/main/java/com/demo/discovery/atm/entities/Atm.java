package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "ATM")
@NoArgsConstructor
@Getter
@Setter
public class Atm {

    @Id
    @NotNull
    @Column(name = "ATM_ID")
    private int atmId;

    @Size(max = 10)
    @NotNull
    @Column(name = "NAME")
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "LOCATION")
    private String location;
}
