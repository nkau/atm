package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CLIENT_ACCOUNT")
@NoArgsConstructor
@Getter
@Setter
public class ClientAccount {

    @Id
    @NotNull
    @Size(max = 10)
    @Column(name = "CLIENT_ACCOUNT_NUMBER")
    private String clientAccountNumber;

    @NotNull
    @Size(max = 10)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "CLIENT_ID")
    private Client client;

    @NotNull
    @Size(max = 10)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_TYPE_CODE",referencedColumnName = "ACCOUNT_TYPE_CODE")
    private AccountType accountType;

    @Size(max = 3)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_CODE",referencedColumnName = "CURRENCY_CODE")
    private Currency currency;

    @Size(min = 3,max = 18)
    @Column(name = "DISPLAY_BALANCE")
    private double displayBalance;
}
