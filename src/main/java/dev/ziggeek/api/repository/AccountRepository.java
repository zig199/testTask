package dev.ziggeek.api.repository;


import java.util.Optional;
import dev.ziggeek.api.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;


public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByUserId(Long userId);
}
