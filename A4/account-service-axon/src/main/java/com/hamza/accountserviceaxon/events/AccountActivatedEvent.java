package com.hamza.accountserviceaxon.events;

import com.hamza.accountserviceaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @Builder @NoArgsConstructor
public class AccountActivatedEvent {
    private String accountId;
    private AccountStatus status;
}
