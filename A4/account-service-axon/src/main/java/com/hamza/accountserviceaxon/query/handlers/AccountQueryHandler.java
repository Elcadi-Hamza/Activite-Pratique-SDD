package com.hamza.accountserviceaxon.query.handlers;

import com.hamza.accountserviceaxon.query.dtos.AccountStatementResponseDTO;
import com.hamza.accountserviceaxon.query.entities.Account;
import com.hamza.accountserviceaxon.query.entities.AccountOperation;
import com.hamza.accountserviceaxon.query.queries.GetAccountStatementQuery;
import com.hamza.accountserviceaxon.query.queries.GetAllAccountsQuery;
import com.hamza.accountserviceaxon.query.queries.WatchEventQuery;
import com.hamza.accountserviceaxon.query.repository.AccountRepository;
import com.hamza.accountserviceaxon.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account,operations);
    }
    @QueryHandler
    public AccountOperation on(WatchEventQuery query){
        return AccountOperation.builder().build();
    }

}
