package com.hamza.accountserviceaxon.commands.dto;

import com.hamza.accountserviceaxon.enums.AccountStatus;

public record UpdateAccountRequestDto(String accountId, AccountStatus status) {
}
