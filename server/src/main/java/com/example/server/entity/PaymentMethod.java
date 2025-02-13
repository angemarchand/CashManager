package com.example.server.entity;

import com.example.server.enums.PaymentMethodType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @Enumerated
    @Column(name = "type", nullable = false)
    private PaymentMethodType type;

    @Column(name = "credit_card_number", unique = true, length = 19)
    private String creditCardNumber;

    @Column(name = "cvc", length = 3)
    private String cvc;

    @Column(name = "validity_date")
    private LocalDateTime validityDate;

    @Column(name = "check_number")
    private Integer checkNumber;

    @Column(name = "cashed")
    private Boolean cashed;

    public static PaymentMethod createCreditCard(Account account, String creditCardNumber, String cvc, LocalDateTime validityDate) {
        return new PaymentMethod(
                null,
                account,
                PaymentMethodType.CARD,
                creditCardNumber,
                cvc,
                validityDate,
                null,
                null);
    }

    public static PaymentMethod createCheck(Account account, int checkNumber, boolean cashed) {
        return new PaymentMethod(
                null,
                account,
                PaymentMethodType.CHECK,
                null,
                null,
                null,
                checkNumber,
                cashed);
    }
}