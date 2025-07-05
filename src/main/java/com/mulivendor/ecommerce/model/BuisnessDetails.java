package com.mulivendor.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuisnessDetails {
    private String buisnessName;
    private String buisnessEmail;
    private String buisnessMobile;
    private String buisnessAddress;
    private String logo;
    private String banner;
}
