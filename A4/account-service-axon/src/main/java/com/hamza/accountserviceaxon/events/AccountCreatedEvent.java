package com.hamza.accountserviceaxon.events;

import com.hamza.accountserviceaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}
//un event est un objet imuable, on just les getters et les constructeur avec parametre
