package com.demo.discovery.atm.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CurrencyBalance implements Comparable<CurrencyBalance>{

    private double currencyBalance;

    private double conversionRate;

    private double multiplicationRate;

    private double zarAmount;

    public CurrencyBalance(double currencyBalance, double conversionRate, double multiplicationRate, double zarAmount) {
        this.currencyBalance = currencyBalance;
        this.conversionRate = conversionRate;
        this.multiplicationRate = multiplicationRate;
        this.zarAmount = zarAmount;
    }

    @Override
    public int compareTo(CurrencyBalance o) {
        return Double.compare(this.zarAmount,o.zarAmount);
    }
}
