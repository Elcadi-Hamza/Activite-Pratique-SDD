package com.hamza.accountserviceaxon.commands.aggregates;

import com.hamza.accountserviceaxon.commands.commands.AddAccountCommand;
import com.hamza.accountserviceaxon.enums.AccountStatus;
import com.hamza.accountserviceaxon.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    //pour AXON
    public AccountAggregate(){}

    //les regles metiers / fonction de decision
    @CommandHandler
    public AccountAggregate(AddAccountCommand command){
        log.info("##################### AddAccountCommand recived ############################");
        if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Balance must be positive");
        //si les regeles metiers sont satisfaite on lance et dipatch les evenment
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("##################### AccountCreatedEvent occured ############################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
