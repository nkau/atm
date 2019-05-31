package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CURRENCY_CONVERSION_RATE")
@NoArgsConstructor
@Getter
@Setter
public class CurrencyConversionRate {

    @Id
    @NotNull
    @Size(max = 3)
    private String currencyCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_CODE",referencedColumnName = "CURRENCY_CODE")
    @MapsId
    private Currency currency;

    @NotNull
    @Size(max = 1)
    @Column(name = "CONVERSION_INDICATOR")
    private String conversionIndicator;

    @NotNull
    @Size(min = 8,max = 18)
    @Column(name = "RATE")
    private double rate;
}
