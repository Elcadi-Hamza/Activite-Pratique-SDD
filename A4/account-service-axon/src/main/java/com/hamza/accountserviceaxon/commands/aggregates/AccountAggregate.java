package com.hamza.accountserviceaxon.commands.aggregates;

import com.hamza.accountserviceaxon.commands.commands.AddAccountCommand;
import com.hamza.accountserviceaxon.commands.commands.CreditAccountCommand;
import com.hamza.accountserviceaxon.commands.commands.DebitAccountCommand;
import com.hamza.accountserviceaxon.enums.AccountStatus;
import com.hamza.accountserviceaxon.events.AccountActivatedEvent;
import com.hamza.accountserviceaxon.events.AccountCreatedEvent;
import com.hamza.accountserviceaxon.events.AccountCreditedEvent;
import com.hamza.accountserviceaxon.events.AccountDebitedEvent;
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
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("##################### AccountCreatedEvent occured ############################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("##################### AccountActivated occured ############################");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command){
        log.info("##################### CreditAccountCommand recived ############################");
        if(!status.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("The account " + command.getId() + " is not activated");
        if(command.getAmmount()<=0) throw  new IllegalArgumentException("Ammount must be positive.");
        //si les regeles metiers sont satisfaite on lance et dipatch les evenment
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("##################### AccountCreditedEvent occured ############################");
        this.accountId = event.getAccountId();
        this.balance = this.balance + event.getAmmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        log.info("##################### DebitAccountCommand recived ############################");
        if(!status.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("The account " + command.getId() + " is not activated");
        if(balance<command.getAmmount()) throw  new RuntimeException("balance not enought exception");
        if(command.getAmmount()<=0) throw  new IllegalArgumentException("Ammount must be positive.");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        log.info("##################### AccountDebitedEvent occured ############################");
        this.accountId = event.getAccountId();
        this.balance = this.balance - event.getAmmount();
    }
}
