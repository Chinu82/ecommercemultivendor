package com.mulivendor.ecommerce.model;

import com.mulivendor.ecommerce.domain.PaymentMethod;
import com.mulivendor.ecommerce.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;
    private String paymentLinkId;

    @ManyToOne
    private Users user;

    @OneToMany
    private Set<Order> orders = new HashSet<>();
}
