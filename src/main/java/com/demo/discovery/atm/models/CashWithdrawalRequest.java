package com.demo.discovery.atm.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CashWithdrawalRequest {

    private int atmId;
    private int clientId;
    private String accountNumber;
    private double requiredAmount;
    private Date timeStamp;
}
