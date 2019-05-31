package com.demo.discovery.atm.services;

import com.demo.discovery.atm.exceptions.ATMSystemException;
import com.demo.discovery.atm.models.CashWithdrawalResponse;
import com.demo.discovery.atm.models.CurrencyBalancesResponse;
import com.demo.discovery.atm.models.TransactionBalancesResponse;
import com.demo.discovery.atm.models.CashWithdrawalRequest;
import org.springframework.stereotype.Component;

@Component
public interface AtmService {

    TransactionBalancesResponse getTransactionalAccountBalances(int clientId) throws ATMSystemException;
    CurrencyBalancesResponse getCurrencyAccountBalances(int clientId) throws ATMSystemException;
    CashWithdrawalResponse doCashWithdrawal(CashWithdrawalRequest withdrawalRequest) throws ATMSystemException;
}
