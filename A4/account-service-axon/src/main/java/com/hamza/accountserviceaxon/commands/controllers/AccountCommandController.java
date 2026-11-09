package com.hamza.accountserviceaxon.commands.controllers;

import com.hamza.accountserviceaxon.commands.dto.AddNewAccountRequestDto;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    @PostMapping("/add")
    public String addNewAccount(@RequestBody AddNewAccountRequestDto requestDto) {
        //dispatching
        commandGateway.send()
        return "";
    }

}
