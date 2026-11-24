package net.salma.billingservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.salma.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryRestClient {

    @GetMapping("/products/{id}")
    @CircuitBreaker(name = "inventoryServiceCB", fallbackMethod = "getDefaultProduct")
    Product getProductById(@PathVariable Long id);

    default Product getDefaultProduct(Long id, Exception exception) {
        return Product.builder()
                .id(id)
                .name("Default Product")
                .price(0.0)
                .quantity(0)
                .build();
    }
}