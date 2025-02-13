package com.example.server.dto;

import com.example.server.enums.PaymentMethodType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentMethodRequest {
    private Long accountId;            // ID du compte auquel la méthode est associée
    private PaymentMethodType type;
    private String creditCardNumber;
    private String cvc;
    private LocalDateTime validityDate;
    private Integer checkNumber;
    private Boolean cashed;
}
