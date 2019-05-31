package com.demo.discovery.atm.repositories;

import com.demo.discovery.atm.entities.Denomination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenominationRepository extends JpaRepository<Denomination, Integer> {
    List<Denomination> findByDenominationDenominationTypeCode(String denominationTypeCode);
}
