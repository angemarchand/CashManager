package com.example.server.controller;

import com.example.server.dto.PaymentMethodRequest;
import com.example.server.entity.Account;
import com.example.server.entity.PaymentMethod;
import com.example.server.enums.PaymentMethodType;
import com.example.server.repository.AccountRepository;
import com.example.server.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodRepository paymentMethodRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository,
                                   AccountRepository accountRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodRequest request) {
        // Récupérer le compte
        Account account = accountRepository.findById(request.getAccountId())
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Compte non trouvé");
        }

        PaymentMethod paymentMethod;
        if (request.getType() == PaymentMethodType.CARD) {
            paymentMethod = PaymentMethod.createCreditCard(account,
                    request.getCreditCardNumber(), request.getCvc(), request.getValidityDate());
        } else if (request.getType() == PaymentMethodType.CHECK) {
            paymentMethod = PaymentMethod.createCheck(account, request.getCheckNumber(), request.getCashed());
        } else {
            return ResponseEntity.badRequest().body("Type de paiement invalide");
        }

        PaymentMethod savedMethod = paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMethod);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentMethod(@PathVariable Long id) {
        return paymentMethodRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Méthode de paiement non trouvée"));
    }
}
