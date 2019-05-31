package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "ACCOUNT_TYPE")
@NoArgsConstructor
@Getter
@Setter
public class AccountType {

    @Id
    @Size(max = 10)
    @NotNull
    @Column(name = "ACCOUNT_TYPE_CODE")
    private String accountTypeCode;

    @Size(max = 50)
    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TRANSACTIONAL")
    private boolean transactional;
}
