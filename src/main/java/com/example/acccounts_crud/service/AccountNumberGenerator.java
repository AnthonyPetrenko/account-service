package com.example.acccounts_crud.service;


import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.DecimalFormat;

@Service
public class AccountNumberGenerator {

    @PersistenceContext
    private EntityManager entityManager;
    private final DecimalFormat df = new DecimalFormat("000000");


    public String generateAccountNumber(){
        BigInteger sequenceValue = (BigInteger) entityManager
                .createNativeQuery("SELECT nextval('account_number_sequence')")
                .getSingleResult();

        String accountNumber = "40702" + df.format(sequenceValue) + "810";

        return accountNumber;
    }

}
