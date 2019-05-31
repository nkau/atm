package com.demo.discovery.atm.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionalBalance implements Comparable<TransactionalBalance>{
    private String accountNumber;
    private String accountType;
    private double accountBalance;

    public TransactionalBalance(String accountNumber,String accountType,
                                double accountBalance){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    @Override
    public int compareTo(TransactionalBalance o) {
        return Double.compare(this.accountBalance,o.accountBalance);
    }
}
