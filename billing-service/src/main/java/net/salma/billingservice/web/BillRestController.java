package net.salma.billingservice.web;

import net.salma.billingservice.entities.Bill;
import net.salma.billingservice.feign.CustomerRestClient;
import net.salma.billingservice.feign.InventoryRestClient;
import net.salma.billingservice.repository.BillRepository;
import net.salma.billingservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillRestController {

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final InventoryRestClient inventoryRestClient;

    public BillRestController(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            InventoryRestClient inventoryRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping("/bills/{id}")
    public Bill getBillById(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).orElseThrow();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi ->
                pi.setProduct(inventoryRestClient.getProductById(pi.getProductId()))
        );
        return bill;
    }
}