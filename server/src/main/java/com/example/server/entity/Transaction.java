package com.example.server.entity;

import com.example.server.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_method_id", nullable = false)
    @ToString.Exclude
    private PaymentMethod paymentMethod;

    @Enumerated
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    public Transaction(PaymentMethod method, TransactionStatus status, BigDecimal amount, String receiver) {
        this.paymentMethod = method;
        this.status = status;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.receiver = receiver;
    }
}