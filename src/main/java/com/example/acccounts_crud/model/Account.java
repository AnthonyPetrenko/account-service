package com.example.acccounts_crud.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Setter
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "project_id")
    private long projectId;

    public Account (String accountNumber,
                    long projectId){
        this.accountNumber = accountNumber;
        this.projectId = projectId;
    }

}