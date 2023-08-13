package com.example.acccounts_crud.repository;

import com.example.acccounts_crud.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void deleteAccountByAccountNumber(long accountNumber);

    boolean existsAccountByAccountNumber(long accountNumber);

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    @Modifying
    @Query("UPDATE Account ac SET ac.projectId=-1 WHERE ac.projectId=?1")
    int removeAllProjectId(long projectId);

}