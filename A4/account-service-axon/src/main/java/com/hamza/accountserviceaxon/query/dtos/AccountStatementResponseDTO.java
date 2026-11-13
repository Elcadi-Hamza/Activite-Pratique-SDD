package com.hamza.accountserviceaxon.query.dtos;

import com.hamza.accountserviceaxon.query.entities.Account;
import com.hamza.accountserviceaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account, List<AccountOperation> operations
) {
}
