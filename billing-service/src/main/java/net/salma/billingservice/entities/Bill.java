package net.salma.billingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.salma.billingservice.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    private Long customerId;

    @OneToMany(mappedBy = "bill")
    @Builder.Default
    private List<ProductItem> productItems = new ArrayList<>();

    @Transient
    private Customer customer;
}