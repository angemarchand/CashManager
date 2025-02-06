package com.example.server.enums;

public enum TransactionStatus {
    PAYMENT_IN_PROGRESS,
    INCORRECT_PAYMENT_INFO,
    INSUFFICIENT_BALANCE,
    INACTIVE_ACCOUNT,
    CANCELED,
    PAYMENT_ACCEPTED,
    ACCOUNT_NOT_FOUND
}