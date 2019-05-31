package com.demo.discovery.atm.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class TransactionBalancesResponse {
    private Set<TransactionalBalance> transactionalBalances;

    public TransactionBalancesResponse(Set<TransactionalBalance> transactionalBalances) {
        this.transactionalBalances = transactionalBalances;
    }
}
