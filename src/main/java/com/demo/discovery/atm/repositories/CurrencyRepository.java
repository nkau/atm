package com.demo.discovery.atm.repositories;

import com.demo.discovery.atm.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,String> {
    Currency findByCurrencyCode(String currencyCode);
}
