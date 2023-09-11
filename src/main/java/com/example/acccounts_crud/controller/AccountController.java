package com.example.acccounts_crud.controller;

import com.example.acccounts_crud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount
            (@RequestParam("project-id") long projectId) {

        if (accountService.createNewAccount(projectId)) {
            return ResponseEntity.ok("Account has been created.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Project with project ID " + projectId + " doesn't exist.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount
            (@RequestParam("account-number") long accountNumber) {

        if (accountService.deleteAccount(accountNumber)) {
            return ResponseEntity
                    .ok("Account was deleted.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Account with ID " + accountNumber + "doesn't exist.");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProjectId
            (@RequestParam("account-number") String accountNumber,
             @RequestParam("project_id") long projectId) {

        if (accountService.changeProjectId(accountNumber, projectId)) {
            return ResponseEntity
                    .ok("New project ID of account with account number "
                            + accountNumber + " is " + projectId);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Account with account number " + accountNumber + " doesn't exist");
        }
    }

    @PostMapping("/remove-all-project-id")
    public ResponseEntity<String> removeAllProjectId
            (@RequestParam("project-id") long projectId) {

        if (accountService.removeAllProjectId(projectId)) {
            return ResponseEntity
                    .ok("Project ID " + projectId + " was removed.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Accounts with project ID " + projectId + " don't exist.");
        }
    }
}