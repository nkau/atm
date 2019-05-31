package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "DENOMINATION_TYPE")
@NoArgsConstructor
@Getter
@Setter
public class DenominationType {

    @Id
    @NotNull
    @Size(max = 1)
    @Column(name = "DENOMINATION_TYPE_CODE")
    private String denominationTypeCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

}
