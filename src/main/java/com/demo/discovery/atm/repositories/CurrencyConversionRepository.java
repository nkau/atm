package com.demo.discovery.atm.repositories;

import com.demo.discovery.atm.entities.CurrencyConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversionRate,String> {
    CurrencyConversionRate findByCurrencyCode(String currencyCode);
}
