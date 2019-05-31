package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Denomination {

    @Id
    @Column(name = "DENOMINATION_ID")
    private int denominationId;

    @NotNull
    @Column(name = "VALUE")
    private double value;

    @Size(max = 1)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DENOMINATION_TYPE_CODE",referencedColumnName = "DENOMINATION_TYPE_CODE")
    private DenominationType denomination;

}
