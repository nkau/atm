package com.demo.discovery.atm.repositories;

import com.demo.discovery.atm.entities.AtmAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmAllocationRepository extends JpaRepository<AtmAllocation,Integer> {
    @Query("SELECT x FROM ATM_ALLOCATION x where x.atm.atmId = :atmId")
    List<AtmAllocation> findByAtmId(int atmId);
}
