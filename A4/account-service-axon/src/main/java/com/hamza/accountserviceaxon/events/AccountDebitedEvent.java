package com.hamza.accountserviceaxon.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @Builder @NoArgsConstructor
public class AccountDebitedEvent {
    private String accountId;
    private double ammount;
    private String currency;
}
