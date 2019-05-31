package com.demo.discovery.atm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "CLIENT")
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @Column(name = "CLIENT_ID")
    @Size(max = 10)
    @NotNull
    private int clientId;

    @Size(max = 10)
    @Column(name = "TITLE")
    private String title;


    @Size(max = 255)
    @NotNull
    @Column(name = "NAME")
    private String name;

    @Size(max = 100)
    @Column(name = "SURNAME")
    private String surname;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DOB")
    @NotNull
    Date dob;

    @OneToOne
    @JoinColumn(name = "CLIENT_SUB_TYPE_CODE",referencedColumnName = "CLIENT_SUB_TYPE_CODE")
    @Size(max = 4)
    @NotNull
    private ClientSubType clientSubType;

}
