package com.demo.discovery.atm.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class CurrencyBalancesResponse {
    private Set<CurrencyBalance> currencyBalances;

    public CurrencyBalancesResponse(Set<CurrencyBalance> currencyBalances) {
        this.currencyBalances = currencyBalances;
    }
}
