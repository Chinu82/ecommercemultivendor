package com.mulivendor.ecommerce.Ecommerce.model;

import com.mulivendor.ecommerce.Ecommerce.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentLinkLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private PaymentStatus status;
}
