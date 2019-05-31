package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CLIENT_SUB_TYPE")
@NoArgsConstructor
@Getter
@Setter
public class ClientSubType {

    @Id
    @NotNull
    @Size(max = 4)
    @Column(name = "CLIENT_SUB_TYPE_CODE")
    private String clientSubTypeCode;

    @NotNull
    @Size(max = 2)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_TYPE_CODE",referencedColumnName = "CLIENT_TYPE_CODE")
    private ClientType clientTypeCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
}
