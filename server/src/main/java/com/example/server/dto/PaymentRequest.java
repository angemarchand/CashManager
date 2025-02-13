package com.example.server.dto;

import com.example.server.enums.PaymentMethodType;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private Long paymentMethodId;
    private PaymentMethodType paymentMethodType;
}
