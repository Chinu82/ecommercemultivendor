package com.mulivendor.ecommerce.Ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetails {
    private String accountNumber;
    private String accountHolder;
    private String ifscCode;
}
