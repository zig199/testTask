package dev.ziggeek.api.service;


import java.math.BigDecimal;
import dev.ziggeek.api.model.entity.Account;
import dev.ziggeek.api.model.dto.request.AccountTransferRequest;
import dev.ziggeek.api.repository.AccountRepository;
import javax.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final BigDecimal MAX_LIMIT = BigDecimal.valueOf(2.07);
    private final BigDecimal PERCENT_BONUS = BigDecimal.valueOf(1.1);

    private final AccountRepository accountRepository;


    public void transfer(@NonNull @Valid AccountTransferRequest request) {
        Account from = findAccountBy(request.getFromUser());
        Account to = findAccountBy(request.getToUser());
        from.subtractFromBalance(request.getAmount());

        to.addBalance(request.getAmount());

        accountRepository.save(from);
        accountRepository.save(to);
    }

    public Account findAccountBy(@NonNull Long userId) {
        return accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("У пользователя не существует аккаунт"));
    }

    public void addBalance() {
        var accounts = accountRepository.findAll();

        for (var it : accounts) {
            var maxBalance = it.getInitialBalance().multiply(MAX_LIMIT);
            var balance = it.getBalance().multiply(PERCENT_BONUS);

            if (balance.compareTo(maxBalance) > 0)
                continue;

            it.setBalance(balance);
        }

        accountRepository.saveAll(accounts);
    }
}
