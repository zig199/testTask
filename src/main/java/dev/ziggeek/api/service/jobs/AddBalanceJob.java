package dev.ziggeek.api.service.jobs;

import dev.ziggeek.api.model.entity.Account;
import dev.ziggeek.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AddBalanceJob {

    private final BigDecimal MAX_LIMIT = BigDecimal.valueOf(2.07);
    private final BigDecimal PERCENT_BONUS = BigDecimal.valueOf(1.1);

    private final AccountRepository accountRepository;


    @Scheduled(fixedDelayString = "30", timeUnit = TimeUnit.SECONDS)
    public void jobIncreasing() {

        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            BigDecimal maxBalance = account.getInitialBalance().multiply(MAX_LIMIT);
            BigDecimal balance = account.getBalance().multiply(PERCENT_BONUS);
            if (balance.compareTo(maxBalance) <= 0) account.setBalance(balance);
        }

        accountRepository.saveAll(accounts);
    }
}