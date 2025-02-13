package com.example.server.configuration;

import com.example.server.entity.Account;
import com.example.server.entity.User;
import com.example.server.enums.AccountState;
import com.example.server.enums.UserRole;
import com.example.server.repository.AccountRepository;
import com.example.server.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class DefaultAccountConfig implements ApplicationRunner {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public DefaultAccountConfig(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail("defaultUser@example.com");
        User defaultUser;
        if (userOpt.isEmpty()) {
            defaultUser = new User("defaultUser", "defaultUser", "defaultUser@example.com", "defaultPassword", UserRole.CLIENT);
            defaultUser = userRepository.save(defaultUser);
            System.out.println("Default user created: " + defaultUser.getEmail());
        } else {
            defaultUser = userOpt.get();
            System.out.println("Default user already exists: " + defaultUser.getEmail());
        }

        Optional<Account> accountOpt = accountRepository.findByUserId(defaultUser.getId());
        if (accountOpt.isEmpty()) {
            Account account = new Account(defaultUser);
            account.setBalance(BigDecimal.valueOf(200));
            account.setOpeningDate(LocalDateTime.now());
            account.setState(AccountState.ACTIVE);
            accountRepository.save(account);
            System.out.println("Default account created for user: " + defaultUser.getEmail());
        } else {
            System.out.println("Default account already exists for user: " + defaultUser.getEmail());
        }


    }
}
