package org.sid.bank_account_service.service;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;

import java.util.List;

public interface AccountService {
    List<BankAccountResponseDTO> accountList();

    BankAccountResponseDTO accountById(String id);

    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountRequestDTO);

    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountRequestDTO);

    Boolean deleteAccount(String id);
}
