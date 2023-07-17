package dev.ziggeek.api.service;


import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.email.EmailRequest;
import dev.ziggeek.api.model.dto.email.EmailUpdateRequest;
import dev.ziggeek.api.model.dto.phone.Phone;
import dev.ziggeek.api.model.dto.phone.PhoneRequest;
import dev.ziggeek.api.model.dto.phone.PhoneUpdateRequest;
import dev.ziggeek.api.model.dto.request.UserSearchRequest;
import dev.ziggeek.api.model.dto.respomse.UserResponse;
import dev.ziggeek.api.model.entity.QUser;
import dev.ziggeek.api.model.entity.User;
import dev.ziggeek.api.model.mapper.UserMapper;
import dev.ziggeek.api.model.querydsl.QPredicates;
import dev.ziggeek.api.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


//    E-mails methods

    public void addUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.getUserId());
        user.addEmail(request.getEmail());

        userRepository.save(user);
    }


    public void removeUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.getUserId());
        user.removeEmail(request.getEmail());

        userRepository.save(user);
    }


    public void updateUserEmail(@NonNull @Valid EmailUpdateRequest request) {
        Email currentEmail = request.getCurrentEmail();
        Email newEmail = request.getNewEmail();

        if (currentEmail.equals(newEmail) || userRepository.existsByEmailsIsContaining(newEmail)) {
            log.warn("*** E-mail {} уже используется пользователем", request.getNewEmail());
            throw new RuntimeException("*** Ошибка!!! Отказано в изменении почты.");
        }

        User user = findById(request.getUserId());
        user.updateEmail(currentEmail, newEmail);

        this.userRepository.save(user);
    }


//    Phone methods


    public void addUserPhone(@NonNull @Valid PhoneRequest request) {
        User user = findById(request.getUserId());
        user.addPhone(request.getPhone());

        userRepository.save(user);
    }


    public void removeUserPhone(@NonNull @Valid PhoneRequest request) {
        User user = findById(request.getUserId());
        user.removePhone(request.getPhone());

        userRepository.save(user);
    }


    public void updateUserPhone(@NonNull @Valid PhoneUpdateRequest request) {
        Phone currentPhoneNumber = request.getCurrentPhoneNumber();
        Phone newPhoneNumber = request.getNewPhoneNumber();

        if (currentPhoneNumber.equals(newPhoneNumber) || userRepository.existsByPhonesIsContaining(newPhoneNumber)) {
            log.warn("*** Номер {} уже используется пользователем", request.getNewPhoneNumber());
            throw new RuntimeException("*** ОШИБКА!!! Отаказано в изменении номера.");
        }

        User user = findById(request.getUserId());
        user.updatePhone(currentPhoneNumber, newPhoneNumber);

        this.userRepository.save(user);
    }


//  Others methods


    public User findById(@NonNull Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("*** Пользователь не найден"));
    }


    public List<UserResponse> search(UserSearchRequest request, Integer page, Integer pageSize) {
        var predicate = QPredicates.builder()
                .add(request.getName(), QUser.user.name::startsWith)
                .add(request.getDateOfBirth(), QUser.user.dateOfBirth::after)
                .add(request.getPhone().getPhone(), QUser.user.phones.any().phone::eq)
                .add(request.getEmail().getEmail(), QUser.user.emails.any().email::eq)
                .buildOr();

        return userRepository
                .findAll(predicate,
                        PageRequest.of(
                                Optional.ofNullable(page).orElse(0),
                                Optional.ofNullable(pageSize).orElse(10)
                        )
                )
                .getContent()
                .stream()
                .map(userMapper::toBasicResp)
                .collect(Collectors.toList());
    }


    public User findByEmail(Email email) {
        return userRepository.findByEmailsContaining(email)
                .orElseThrow(() -> new UsernameNotFoundException("*** Ошибка!!! Пользователь с e-mail не найден."));
    }
}
