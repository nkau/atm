package com.demo.discovery.atm.services;

import com.demo.discovery.atm.entities.Currency;
import com.demo.discovery.atm.entities.*;
import com.demo.discovery.atm.exceptions.ATMSystemException;
import com.demo.discovery.atm.models.*;
import com.demo.discovery.atm.repositories.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AtmServiceImpl implements AtmService {

    @Autowired
    private ClientAccountRepository clientAccountRepository;

    @Autowired
    private DenominationRepository denominationRepository;

    @Autowired
    private AtmAllocationRepository atmAllocationRepository;

    @Autowired
    private CurrencyConversionRepository currencyConversionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private static final String ALLOWED_DENOMINATION_TYPE_CODE = "N";

    private static final String CURRENCY_ACCOUNT_TYPE_CODE = "CFCA";

    private MultiValuedMap<Double, Double> availableNotesSetForRequest = new ArrayListValuedHashMap<>();

    @Override
    public TransactionBalancesResponse getTransactionalAccountBalances(int clientId) throws ATMSystemException {
        final boolean TRANSACTIONAL_STATUS = true;
        List<ClientAccount> clientAccounts = clientAccountRepository.findByClientIdAndTransactionalStatus(clientId,TRANSACTIONAL_STATUS);
        if(clientAccounts.isEmpty()){
            throw new ATMSystemException("No accounts to display.");
        }
        Set<TransactionalBalance> orderedTransactionalBalances = buildOrderedTransactionalBalanceSet(clientAccounts);
        return new TransactionBalancesResponse(orderedTransactionalBalances);
    }

    @Override
    public CurrencyBalancesResponse getCurrencyAccountBalances(int clientId) throws ATMSystemException {

        List<ClientAccount> clientCurrencyAccounts = clientAccountRepository.findByClientIDAndAccountTypeCode(clientId,CURRENCY_ACCOUNT_TYPE_CODE);
        if(clientCurrencyAccounts.isEmpty()){
            throw new ATMSystemException("The client does not have any qualifying accounts");
        }
        Set<CurrencyBalance> orderedCurrencyBalances = buildOrderedCurrencyBalanceSet(clientCurrencyAccounts);
        return new CurrencyBalancesResponse(orderedCurrencyBalances);
    }

    @Override
    public CashWithdrawalResponse doCashWithdrawal(CashWithdrawalRequest withdrawalRequest) throws ATMSystemException {

        double availableBalance = determineCurrentClientAccountBalance(withdrawalRequest.getClientId(), withdrawalRequest.getAccountNumber());

        double totalFundsAvailableForAtm = computeAvailableFundsForAtm(withdrawalRequest.getAtmId());

        double requiredAmount = withdrawalRequest.getRequiredAmount();

        if(!clientHasSufficientFunds(availableBalance,requiredAmount)){
            throw new ATMSystemException("Insufficient funds");
        }

        if(!canDispenseRequestedAmount(requiredAmount,totalFundsAvailableForAtm)){
            int possibleAmount = (int)requiredAmount;
            throw new ATMSystemException("Amount not available, would you like to draw " + possibleAmount);
        }

        Map<Integer, Double> denominationMap = buildDenominationMap();

        MultiValuedMap<Double, Double> notesToDispense = computeNotesToDispense(requiredAmount, denominationMap);

        return buildCashWithdrawalResponse(notesToDispense);
    }

    private CashWithdrawalResponse buildCashWithdrawalResponse(MultiValuedMap<Double,Double> notesToDispense){
        Map<Double,Double> quantityMap = new HashMap<>();
        Set<Double> keys = notesToDispense.keySet();
        for(Double key : keys){
            Collection<Double> valuesForKey = notesToDispense.get(key);
            for(Double valueForKey : valuesForKey){
                quantityMap.put(valueForKey,key);
            }
        }
        return new CashWithdrawalResponse(quantityMap);
    }

    private boolean canDispenseRequestedAmount(double requiredAmount,double totalFundsAvailableForAtm){
        return (requiredAmount % 10 == 0 && requiredAmount < totalFundsAvailableForAtm);
    }

    private boolean clientHasSufficientFunds(double availableAmount,double requiredAmount){
        return availableAmount > requiredAmount;
    }

    private double determineCurrentClientAccountBalance(int clientId, String accountNumber) throws ATMSystemException {
        ClientAccount clientAccount = clientAccountRepository.findByClientIdAndClientAccountNumber(clientId, accountNumber);
        if(clientAccount == null){
            throw new ATMSystemException("The client does not have any qualifying accounts");
        }
        return clientAccount.getDisplayBalance();
    }

    private double computeAvailableFundsForAtm(int atmId){
        double total = 0L;
        List<AtmAllocation> atmAllocations = atmAllocationRepository.findByAtmId(atmId);
        for(AtmAllocation atmAllocation : atmAllocations){
            double denominationValue = atmAllocation.getDenomination().getValue();
            int denominationCount = atmAllocation.getCount();

            double totalForDenomination = denominationValue * denominationCount;

            total = total + totalForDenomination;
        }

        return total;
    }

    private Set<TransactionalBalance> buildOrderedTransactionalBalanceSet(List<ClientAccount> clientAccounts){
        TreeSet<TransactionalBalance> transactionalBalances = new TreeSet<>();

        for(ClientAccount clientAccount : clientAccounts){
            TransactionalBalance transactionalBalance = new TransactionalBalance(clientAccount.getClientAccountNumber(),
                    clientAccount.getAccountType().getDescription(),clientAccount.getDisplayBalance());

            transactionalBalances.add(transactionalBalance);
        }
        return transactionalBalances.descendingSet();
    }

    private Set<CurrencyBalance> buildOrderedCurrencyBalanceSet(List<ClientAccount> clientAccounts){
        TreeSet<CurrencyBalance> currencyBalances = new TreeSet<>();
        CurrencyBalance currencyBalance;

        for(ClientAccount clientAccount : clientAccounts){

            String currencyCode = clientAccount.getCurrency().getCurrencyCode();
            double currentBalance = clientAccount.getDisplayBalance();

            CurrencyConversionRate currencyConversion = currencyConversionRepository.findByCurrencyCode(currencyCode);
            Currency currency  = currencyRepository.findByCurrencyCode(currencyCode);

            double conversionRate = currencyConversion.getRate();
            double multiplicationRate = determineMultiplicationRate(currencyConversion);
            double randAmount = currentBalance * multiplicationRate;

            BigDecimal zarAmount = BigDecimal.valueOf(randAmount);
            BigDecimal returnValue = zarAmount.setScale(currency.getDecimalPlaces(), RoundingMode.HALF_UP);

            currencyBalance = new CurrencyBalance(currentBalance,conversionRate,returnValue.doubleValue(),randAmount);

            currencyBalances.add(currencyBalance);
        }

        return currencyBalances;
    }

    private double determineMultiplicationRate(CurrencyConversionRate currencyConversionRate){
        String conversionIndicator = currencyConversionRate.getConversionIndicator();
        double conversionRate = currencyConversionRate.getRate();

        if(conversionIndicator.equalsIgnoreCase("*")){
            return conversionRate;
        }
        return 1/conversionRate;
    }

    private MultiValuedMap<Double, Double> computeNotesToDispense(double requiredAmount,Map dispensationMap){
        Collection<Double> availableNoteValues = dispensationMap.values();
        return fundsFromAvailableNoteValues(requiredAmount, availableNoteValues);
    }

    private MultiValuedMap<Double, Double> fundsFromAvailableNoteValues(double requiredAmount,Collection<Double> availableNotes){

        for(double noteValue : availableNotes){

            double quotient = requiredAmount/noteValue;
            double remainder = requiredAmount%noteValue;

            if(quotient >= 1 && remainder ==0){
                availableNotesSetForRequest.put(quotient,noteValue);
                double dispensedAmount = quotient * noteValue;
                requiredAmount = requiredAmount - dispensedAmount;

                if(requiredAmount < 10){
                    break;
                }
            }
            if(quotient > 1 && remainder != 0){
                int intValueOfQuotient = (int) quotient;
                availableNotesSetForRequest.put(Double.valueOf(intValueOfQuotient),noteValue);
                double dispensedAmount = intValueOfQuotient * noteValue;
                requiredAmount = requiredAmount - dispensedAmount;

                if(requiredAmount < 10){
                    break;
                }

                fundsFromAvailableNoteValues(remainder,availableNotes);
            }
        }
        return availableNotesSetForRequest;
    }

    private Map<Integer,Double> buildDenominationMap(){
        List<Denomination> denominations = denominationRepository.findByDenominationDenominationTypeCode(ALLOWED_DENOMINATION_TYPE_CODE);
        TreeMap<Integer,Double> denominationsMap = new TreeMap<>(Comparator.reverseOrder());
        for(Denomination denomination : denominations){
            denominationsMap.put(denomination.getDenominationId(),denomination.getValue());
        }
        return denominationsMap;
    }

}
