package com.example.acccounts_crud.service;

import com.example.acccounts_crud.feign_client.ProjectFeignClient;
import com.example.acccounts_crud.kafka.ProjectIdProducer;
import com.example.acccounts_crud.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import com.example.acccounts_crud.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@EnableFeignClients
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final ProjectFeignClient feignClient;
    private final ProjectIdProducer projectIdProducer;


    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AccountNumberGenerator accountNumberGenerator,
                          ProjectFeignClient feignClient,
                          ProjectIdProducer projectIdProducer) {
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.feignClient = feignClient;
        this.projectIdProducer = projectIdProducer;
    }

    public boolean createNewAccount(long projectId) {

        if (checkIfProjectExist(projectId)) {

            String accountNumber = accountNumberGenerator.generateAccountNumber();
            accountRepository.save(new Account(accountNumber, projectId));

            log.info("Account has been created. Account number: " + accountNumber
                    + ". Project ID " + projectId);

            projectIdProducer.sendProjectId("testTopic", projectId);

            return true;
        } else {
            log.info("Project with project ID " + projectId +
                    " doesn't exist. Account hasn't been created.");

            return false;
        }
    }

    public boolean changeProjectId(String accountNumber,
                                   long newProjectId) {

        Optional<Account> accountOptional = accountRepository
                .findAccountByAccountNumber(accountNumber);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setProjectId(newProjectId);
            accountRepository.save(account);

            log.info("Project ID of account with account number " + accountNumber +
                    " has been changed to project ID " + newProjectId);

            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteAccount(long accountNumber) {

        if (accountRepository.existsAccountByAccountNumber(accountNumber)) {
            accountRepository.deleteAccountByAccountNumber(accountNumber);

            log.info("Account with account number " + accountNumber + " has been deleted.");

            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean removeAllProjectId(long projectId) {

        int isRemoved = accountRepository.removeAllProjectId(projectId);

        String logMessage = isRemoved > 0
                ? String.format("All project ID '%d' was removed.", projectId)
                : String.format("Accounts with project ID '%d' not found.", projectId);

        log.info(logMessage);

        return isRemoved > 0;
    }

    private boolean checkIfProjectExist(long projectId) {
        return feignClient.checkIfProjectExist(projectId);
    }
}