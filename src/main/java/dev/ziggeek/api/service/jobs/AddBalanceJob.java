package dev.ziggeek.api.service.jobs;

import dev.ziggeek.api.lock.SharedLockService;
import dev.ziggeek.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
public class AddBalanceJob {

    private static final String LOCK_KEY = "accounts.balance.increase";
    private final SharedLockService.SharedLock increaseLock;
    private final AccountService accountService;


    public AddBalanceJob(AccountService accountService, SharedLockService sharedLockService) {
        this.accountService = accountService;
        this.increaseLock = sharedLockService.getLock(LOCK_KEY);
    }


    @Scheduled(cron = "0/30 * * * * *")
    public void jobIncreasing() {
        if (increaseLock.lock()) {
            log.info("Current pod is selected as responsible for balance processing");
            accountService.addBalance();
        } else {
            log.debug("This pod is not responsible for balance increasing");
        }
    }
}