package com.demo.discovery.atm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter @AllArgsConstructor
public class CashWithdrawalResponse {

    private Map<Double, Double> dispensationMap;
}
