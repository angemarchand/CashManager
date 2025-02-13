package com.example.server.service;

import com.example.server.dto.PaymentRequest;
import com.example.server.entity.Account;
import com.example.server.entity.Order;
import com.example.server.entity.PaymentMethod;
import com.example.server.entity.Transaction;
import com.example.server.enums.TransactionStatus;
import com.example.server.repository.AccountRepository;
import com.example.server.repository.OrderRepository;
import com.example.server.repository.PaymentMethodRepository;
import com.example.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction processPayment(PaymentRequest paymentRequest) {
        // Récupérer la commande
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Récupérer la méthode de paiement
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentRequest.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Méthode de paiement non trouvée"));

        // Récupérer le compte associé à la méthode de paiement
        Account account = paymentMethod.getAccount();
        if (account == null) {
            throw new RuntimeException("Aucun compte associé à la méthode de paiement");
        }

        // Vérifier que le solde est suffisant
        BigDecimal orderTotal = order.getTotalPrice();
        if (account.getBalance().compareTo(orderTotal) < 0) {
            throw new RuntimeException("Balance insuffisante");
        }

        // Créer une transaction avec le montant de la commande
        Transaction transaction = new Transaction(paymentMethod,
                TransactionStatus.PAYMENT_IN_PROGRESS,
                orderTotal,
                "Paiement de la commande");
        transaction = transactionRepository.save(transaction);

        // Simuler le paiement en déduisant le montant du solde du compte
        account.setBalance(account.getBalance().subtract(orderTotal));
        accountRepository.save(account);

        // Mettre à jour le statut de la transaction en succès
        transaction.setStatus(TransactionStatus.PAYMENT_ACCEPTED);
        transaction = transactionRepository.save(transaction);

        return transaction;
    }
}
