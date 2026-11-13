package com.hamza.accountserviceaxon.commands.commands;

import com.hamza.accountserviceaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
