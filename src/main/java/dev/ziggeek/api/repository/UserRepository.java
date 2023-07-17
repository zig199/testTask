package dev.ziggeek.api.repository;

import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import dev.ziggeek.api.model.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Page<User> findAll(Predicate predicate, Pageable pageable);

    Optional<User> findByEmailsContaining(Email email);

    boolean existsByPhonesIsContaining(Phone phone);

    boolean existsByEmailsIsContaining(Email email);

}
