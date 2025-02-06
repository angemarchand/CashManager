package com.example.server.service;

import com.example.server.entity.Transaction;
import com.example.server.enums.TransactionStatus;
import com.example.server.entity.Account;
import com.example.server.enums.AccountState;
import com.example.server.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class in charge of check detail from Account model
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Used to check if account is ACTIVE.
     * Create log otherwise
     * @param accountId of user which want to pay
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> checkAccountValidity(Long accountId, Transaction transaction) {

        Optional<Account> accountOptional  = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            transaction.setStatus(TransactionStatus.ACCOUNT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compte introuvable");
        }
        Account account = accountOptional.get();

        // Vérifier que le compte est actif
        boolean isActive = account.getState() == AccountState.ACTIVE;

        boolean sufficientBalance = account.getBalance().compareTo(transaction.getAmount()) >= 0;

        if (!isActive && !sufficientBalance) {
            transaction.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transaction.setStatus(TransactionStatus.INACTIVE_ACCOUNT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Compte inactif et solde insuffisant");

        } else if (!isActive) {
            transaction.setStatus(TransactionStatus.INACTIVE_ACCOUNT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Compte inactif");

        } else if (!sufficientBalance) {
            transaction.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solde insuffisant");
        }
        transaction.setStatus(TransactionStatus.PAYMENT_IN_PROGRESS);
        return ResponseEntity.ok("Compte valide, paiement en cours");
    }
}