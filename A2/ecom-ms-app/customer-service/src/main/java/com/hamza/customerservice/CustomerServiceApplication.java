package com.hamza.customerservice;

import com.hamza.customerservice.entities.Customer;
import com.hamza.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;


@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start (CustomerRepository customerRepository) {
      return args -> {
          Stream.of("name1", "name2", "name3").forEach(n -> {
              Customer customer = Customer.builder()
                      .name(n)
                      .email(n + "@gmail.com")
                      .build();
              customerRepository.save(customer);
          });
      };
    }

}
