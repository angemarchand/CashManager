package com.example.server.repository;

import com.example.server.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByCreditCardNumberAndCvc(String creditCardNumber, String cvc);

    Optional<PaymentMethod> findByCheckNumber(int checkNumber);
}