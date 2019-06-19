package com.demo.discovery.atm;

import com.demo.discovery.atm.entities.*;
import com.demo.discovery.atm.repositories.*;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

    @Autowired
    private AtmAllocationRepository atmAllocationRepository;

    @Autowired
    private ClientAccountRepository clientAccountRepository;

    @Autowired
    private CurrencyConversionRepository currencyConversionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private DenominationRepository denominationRepository;

    @Test
    public void whenFindByDenominationTypeCode_thenReturnDenominations(){
        final String NOTE_DENOMINATION_TYPE = "N";
        final int NUMBER_OF_NOTE_TYPES = 5;
        List<Denomination> denominations = denominationRepository.findByDenominationDenominationTypeCode(NOTE_DENOMINATION_TYPE);
        assertThat(denominations.size()).isEqualTo(NUMBER_OF_NOTE_TYPES);
    }


    @Test
    public void whenFindByCurrencyCode_thenReturnCurrency(){
        final String ZAMBIAN_CURRENCY_CODE = "ZMW";
        Currency currency = currencyRepository.findByCurrencyCode(ZAMBIAN_CURRENCY_CODE);
        AssertionsForClassTypes.assertThat(currency.getDescription()).isEqualToIgnoringCase("Zambian kwacha");
    }


    @Test
    public void whenFindByCurrencyCode_thenReturnCurrencyConversion(){
        final String JAPANESE_CURRENCY_CODE = "JPY";
        CurrencyConversionRate currencyConversion = currencyConversionRepository.findByCurrencyCode(JAPANESE_CURRENCY_CODE);
        AssertionsForClassTypes.assertThat(currencyConversion).isNotNull();
        AssertionsForClassTypes.assertThat(currencyConversion.getRate()).isEqualTo(9.32);
        AssertionsForClassTypes.assertThat(currencyConversion.getConversionIndicator()).isEqualTo("/");
    }


    @Test
    public void whenFindByAtmId_thenReturnListOfAtms(){
        List<AtmAllocation> atmAllocations = atmAllocationRepository.findByAtmId(1);
        assertThat(atmAllocations).isNotEmpty();
        AssertionsForClassTypes.assertThat(atmAllocations.size()).isEqualTo(5);
    }

    @Test
    public void whenFindByClientIdAndTransactionalStatus_thenReturnClientAccounts(){
        List<ClientAccount> clientAccounts = clientAccountRepository.findByClientIdAndTransactionalStatus(1, true);
        assertThat(clientAccounts).isNotEmpty();
        assertThat(clientAccounts.size()).isEqualTo(3);
    }

    @Test
    public void whenFindByClientIDAndAccountTypeCode_thenReturnClientAccounts(){
        final String HOME_LOAD_ACCOUNT_CODE = "HLOAN";
        List<ClientAccount> clientAccounts = clientAccountRepository.findByClientIDAndAccountTypeCode(1, HOME_LOAD_ACCOUNT_CODE);
        assertThat(clientAccounts.size()).isEqualTo(1);
    }

    @Test
    public void whenFindByClientIdAndClientAccountNumber_thenReturnClientAccount(){
        final String SAVINGS_ACCOUNT_CODE = "SVGS";
        final String SAVINGS_ACCOUNT_NUMBER = "1053664521";
        ClientAccount clientAccount = clientAccountRepository.findByClientIdAndClientAccountNumber(1, SAVINGS_ACCOUNT_NUMBER);
        AssertionsForClassTypes.assertThat(clientAccount.getAccountType().getAccountTypeCode()).isEqualToIgnoringCase(SAVINGS_ACCOUNT_CODE);
    }

}
