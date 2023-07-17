package dev.ziggeek.api.service;


import dev.ziggeek.api.model.dto.request.AccountTransferRequest;
import dev.ziggeek.api.model.entity.Account;
import dev.ziggeek.api.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

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
}
