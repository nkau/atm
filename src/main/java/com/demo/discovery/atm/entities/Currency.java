package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CURRENCY")
@NoArgsConstructor
@Getter
@Setter
public class Currency {

    @Id
    @NotNull
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @NotNull
    @Size(max = 10)
    @Column(name = "DECIMAL_PLACES")
    private int decimalPlaces;

    @NotNull
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
}
