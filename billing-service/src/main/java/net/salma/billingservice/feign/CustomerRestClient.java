package net.salma.billingservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.salma.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "getDefaultCustomer")
    Customer findCustomerById(@PathVariable Long id);

    default Customer getDefaultCustomer(Long id, Exception exception) {
        return Customer.builder()
                .id(id)
                .name("Default Customer")
                .email("default@gmail.com")
                .build();
    }
}