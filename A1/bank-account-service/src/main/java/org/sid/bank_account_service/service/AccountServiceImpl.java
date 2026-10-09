package org.sid.bank_account_service.service;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.mapper.BankAccountMapper;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    // you can intiat the mapper or make the methode static and work directly with it
    // private BankAccountMapper bankAccountMapper;

    @Override
    public List<BankAccountResponseDTO> accountList(){
        return BankAccountMapper.toBankAccountResponseDTOList(bankAccountRepository.findAll());
    }

    @Override
    public BankAccountResponseDTO accountById(String id){
        return BankAccountMapper.toBankAccountResponseDTO(bankAccountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id))));
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        // create the object using the mapper
        BankAccount bankAccount = BankAccountMapper.fromBankAccountRequestDTO(bankAccountRequestDTO);
        // save the object
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        // copy the object or return it directly
        return BankAccountMapper.toBankAccountResponseDTO(savedBankAccount);
    }
    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountRequestDTO) {
        bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
        BankAccount bankAccount = BankAccount.builder()
                .id(id)
                .balance(bankAccountRequestDTO.getBalance())
                .currency(bankAccountRequestDTO.getCurrency())
                .type(bankAccountRequestDTO.getType())
                .build();
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return BankAccountMapper.toBankAccountResponseDTO(savedBankAccount);
    }

    @Override
    public Boolean deleteAccount(String id) {
        bankAccountRepository.findById(id)
                        .orElseThrow(()->new RuntimeException(String.format("account %s not found",id)));
        bankAccountRepository.deleteById(id);
        return true;
    }
}
