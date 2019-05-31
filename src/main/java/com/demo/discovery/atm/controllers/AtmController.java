package com.demo.discovery.atm.controllers;

import com.demo.discovery.atm.exceptions.ATMSystemException;
import com.demo.discovery.atm.models.CashWithdrawalRequest;
import com.demo.discovery.atm.models.CashWithdrawalResponse;
import com.demo.discovery.atm.models.CurrencyBalancesResponse;
import com.demo.discovery.atm.models.TransactionBalancesResponse;
import com.demo.discovery.atm.services.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AtmController {

    @Autowired
    private AtmService atmService;

    @GetMapping(path = "/getTransactionalAccountBalances/{clientId}")
    public ResponseEntity<TransactionBalancesResponse> getTransactionalAccountBalances(@PathVariable int clientId) throws ATMSystemException {
        return ResponseEntity.ok(atmService.getTransactionalAccountBalances(clientId));
    }

    @GetMapping(path = "/getCurrencyAccountBalances/{clientId}")
    public ResponseEntity<CurrencyBalancesResponse> getCurrencyAccountBalances(@PathVariable int clientId) throws ATMSystemException {
        return ResponseEntity.ok(atmService.getCurrencyAccountBalances(clientId));
    }

    @PostMapping(path = "/withdrawCash")
    public ResponseEntity<CashWithdrawalResponse> withdrawCash(@RequestBody CashWithdrawalRequest withdrawalRequest) throws ATMSystemException {
        return ResponseEntity.ok(atmService.doCashWithdrawal(withdrawalRequest));
    }
}
