package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CREDIT_CARD_LIMIT")
@NoArgsConstructor
@Getter
@Setter
public class CreditCardLimit {

    @Id
    @NotNull
    @Size(max = 10)
    private int clientAccountNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER",referencedColumnName = "CLIENT_ACCOUNT_NUMBER")
    private ClientAccount clientAccount;

    @NotNull
    @Size(min = 3,max = 18)
    @Column(name = "ACCOUNT_LIMIT")
    private double accountLimit;
}
