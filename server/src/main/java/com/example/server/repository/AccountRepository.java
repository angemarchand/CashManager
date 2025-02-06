package com.example.server.repository;

import com.example.server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {
    // get all accounts from a user
    Iterable<Account> findAllByUser_Id(Long id);
}