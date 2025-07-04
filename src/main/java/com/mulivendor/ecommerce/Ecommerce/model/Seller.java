package com.mulivendor.ecommerce.Ecommerce.model;

import com.mulivendor.ecommerce.Ecommerce.domain.AccountStatus;
import com.mulivendor.ecommerce.Ecommerce.domain.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;
    private String mobile;

    @Email
    private String email;
    private String password;

    @Embedded
    private BuisnessDetails buisnessDetails = new BuisnessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickUpAddress = new Address();

    private String GSTIN;

    private USER_ROLE role;

    private boolean isEmailVerified = false;
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
}
