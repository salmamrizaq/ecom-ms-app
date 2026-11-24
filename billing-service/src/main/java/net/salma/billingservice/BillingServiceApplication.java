package net.salma.billingservice;

import net.salma.billingservice.entities.Bill;
import net.salma.billingservice.entities.ProductItem;
import net.salma.billingservice.repository.BillRepository;
import net.salma.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository) {
        return args -> {
            List<Long> customerIds = List.of(1L, 2L, 3L);
            List<Long> productIds = List.of(1L, 2L, 3L);
            Random random = new Random();

            customerIds.forEach(customerId -> {
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(customerId)
                        .build();
                billRepository.save(bill);

                productIds.forEach(productId -> {
                    ProductItem productItem = ProductItem.builder()
                            .bill(bill)
                            .productId(productId)
                            .quantity(1 + random.nextInt(20))
                            .unitPrice(1000 + Math.random() * 600)
                            .build();
                    productItemRepository.save(productItem);
                });
            });
        };
    }
}