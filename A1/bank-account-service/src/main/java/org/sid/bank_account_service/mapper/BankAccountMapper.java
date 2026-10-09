package org.sid.bank_account_service.mapper;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BankAccountMapper {

    public static BankAccount fromBankAccountRequestDTO (BankAccountRequestDTO bankAccountRequestDTO) {
        return BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountRequestDTO.getBalance())
                .type(bankAccountRequestDTO.getType())
                .currency(bankAccountRequestDTO.getCurrency())
                .customer(bankAccountRequestDTO.getCustomer())
                .build();
    }
    public static BankAccountResponseDTO toBankAccountResponseDTO (BankAccount bankAccount) {
        return BankAccountResponseDTO.builder()
                .id(bankAccount.getId())
                .createdAt(bankAccount.getCreatedAt())
                .balance(bankAccount.getBalance())
                .type(bankAccount.getType())
                .currency(bankAccount.getCurrency())
                .customer(bankAccount.getCustomer())
                .build();
    }
    public static List<BankAccountResponseDTO> toBankAccountResponseDTOList (List<BankAccount> bankAccounts) {
        return bankAccounts.stream().map(BankAccountMapper::toBankAccountResponseDTO).toList();
    }
}
