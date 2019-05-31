package com.demo.discovery.atm.repositories;

import com.demo.discovery.atm.entities.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount,String> {

    @Query("SELECT x FROM CLIENT_ACCOUNT x where x.client.clientId = :clientId and x.accountType.transactional = :transactionalStatus")
    List<ClientAccount> findByClientIdAndTransactionalStatus(int clientId,boolean transactionalStatus);

    @Query("SELECT x FROM CLIENT_ACCOUNT x where x.client.clientId = :clientId and x.accountType.accountTypeCode = :accountTypeCode")
    List<ClientAccount> findByClientIDAndAccountTypeCode(int clientId,String accountTypeCode);

    @Query("SELECT x FROM CLIENT_ACCOUNT x where x.client.clientId = :clientId and x.clientAccountNumber = :clientAccountNumber")
    ClientAccount findByClientIdAndClientAccountNumber(int clientId,String clientAccountNumber);
}
