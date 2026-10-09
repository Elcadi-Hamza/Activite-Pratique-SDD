package org.sid.bank_account_service;

import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.entities.Customer;
import org.sid.bank_account_service.enums.AccountType;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.sid.bank_account_service.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
		return args -> {
			Stream.of("name1","name2","name3","name4").forEach(c -> {
				Customer customer = Customer.builder()
						.name(c)
						.build();

				customerRepository.save(customer);

			});
			customerRepository.findAll().forEach(customer -> {
				for (int i = 0 ; i < 10 ; i++){
					//there are 3 ways to initialize the bankAccount
					//Using the constructor with no arguments
					//BankAccount bankAccount = new BankAccount();
					//Using the constructor with arguments
					//BankAccount bankAccount = new BankAccount(1,12-12-2026,12,"testing","SAVING_ACCOUNT");
					//Or usign a builder (@Builder)
					BankAccount bankAccount = BankAccount.builder()
							.id(UUID.randomUUID().toString())
							.type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT: AccountType.SAVING_ACCOUNT)
							.balance(1000+Math.random()*90000)
							.createdAt(new Date())
							.currency("MAD")
							.customer(customer)
							.build();
					bankAccountRepository.save(bankAccount);
				}
			});

		};
	}
}
