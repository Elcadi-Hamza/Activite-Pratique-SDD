package com.hamza.inventoryservice;

import com.hamza.inventoryservice.entities.Product;
import com.hamza.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start (ProductRepository productRepository) {
        return args -> {
            Stream.of("product1","product2","product3").forEach(p -> {
                Product product = Product.builder()
                        .name(p)
                        .price(Math.round(Math.random() * 1600 * 100.0) / 100.0)
                        .quantity((int) (Math.random()*1000))
                        .build();
                productRepository.save(product);
            });
        };
    }

}
