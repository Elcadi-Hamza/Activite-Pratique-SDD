package org.sid.bank_account_service.web;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.entities.Customer;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.sid.bank_account_service.repositories.CustomerRepository;
import org.sid.bank_account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;

    @QueryMapping
    public List<BankAccountResponseDTO> accountList(){
        return accountService.accountList();
    }

    @QueryMapping(name = "accountById")
    public BankAccountResponseDTO bankAccountById (@Argument String id) {
        return accountService.accountById(id);
    }

    @MutationMapping
    public BankAccountResponseDTO addAccount (@Argument BankAccountRequestDTO bankAccount) {
        return accountService.addAccount(bankAccount);
    }

    @MutationMapping
    public BankAccountResponseDTO updateAccount (@Argument String id,@Argument BankAccountRequestDTO bankAccount) {
        return accountService.updateAccount(id,bankAccount);
    }
    @MutationMapping
    public Boolean deleteAccount (@Argument String id){
        return accountService.deleteAccount(id);
    }

    @Autowired
    CustomerRepository customerRepository;
    @QueryMapping
    public List<Customer> customerList(){
        return customerRepository.findAll();
    }
}
// you can either work with dtos or create a record
//record bankAccountDto (double banlance, String type, String currency) {
//
//}

