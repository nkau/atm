package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "ATM_ALLOCATION")
@NoArgsConstructor
@Getter
@Setter
public class AtmAllocation {

    @Id
    @NotNull
    @Column(name = "ATM_ALLOCATION_ID")
    @Size(max = 10)
    private int atmAllocationId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @Size(max = 10)
    @JoinColumn(name = "ATM_ID",referencedColumnName = "ATM_ID")
    private Atm atm;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @Size(max = 10)
    @JoinColumn(name = "DENOMINATION_ID",referencedColumnName = "DENOMINATION_ID")
    private Denomination denomination;

    @NotNull
    @Column(name = "COUNT")
    @Size(max = 10)
    private int count;
}
