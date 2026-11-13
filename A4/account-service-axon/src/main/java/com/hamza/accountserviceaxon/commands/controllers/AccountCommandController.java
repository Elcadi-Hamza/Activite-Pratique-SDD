package com.hamza.accountserviceaxon.commands.controllers;

import com.hamza.accountserviceaxon.commands.commands.AddAccountCommand;
import com.hamza.accountserviceaxon.commands.commands.CreditAccountCommand;
import com.hamza.accountserviceaxon.commands.commands.DebitAccountCommand;
import com.hamza.accountserviceaxon.commands.dto.AddNewAccountRequestDto;
import com.hamza.accountserviceaxon.commands.dto.CreditAccountRequestDto;
import com.hamza.accountserviceaxon.commands.dto.DebitAccountRequestDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDto requestDto) {
        //dispatching //<String> car targetAgrregate is a sting
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                requestDto.initialBalance(),
                requestDto.currency()
        ));
        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto requestDto) {
        //dispatching //<String> car targetAgrregate is a sting
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                requestDto.accountId(),
                requestDto.ammount(),
                requestDto.currency()
        ));
        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDto requestDto) {
        //dispatching //<String> car targetAgrregate is a sting
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                requestDto.accountId(),
                requestDto.ammount(),
                requestDto.currency()
        ));
        return response;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountRequestDto requestDto) {
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                requestDto.accountId(),
                requestDto.ammount(),
                requestDto.currency()
        ));
        return response;
    }
}
