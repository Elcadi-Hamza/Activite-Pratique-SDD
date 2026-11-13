package com.hamza.accountserviceaxon.events;

import com.hamza.accountserviceaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @Builder @NoArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}
//un event est un objet imuable, on just les getters et les constructeur avec parametre
