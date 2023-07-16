package dev.ziggeek.api.service;


import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import dev.ziggeek.api.model.dto.request.UserSearchRequest;
import dev.ziggeek.api.model.dto.respomse.UserResponse;
import dev.ziggeek.api.model.entity.User;
import dev.ziggeek.api.model.dto.request.LoginRequest;
import dev.ziggeek.api.model.dto.email.EmailRequest;
import dev.ziggeek.api.model.dto.email.EmailUpdateRequest;
import dev.ziggeek.api.model.dto.phone.PhoneRequest;
import dev.ziggeek.api.model.dto.phone.PhoneUpdateRequest;
import dev.ziggeek.api.model.mapper.UserMapper;
import dev.ziggeek.api.model.querydsl.QPredicates;
import dev.ziggeek.api.repository.UserRepository;

import javax.validation.Valid;

import dev.ziggeek.api.model.entity.QUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;
import java.util.List;


@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


//    E-mails methods


    public void removeUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.getUserId());
        user.removeEmail(request.getEmail());

        userRepository.save(user);
    }


    public void addUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.getUserId());
        user.addEmail(request.getEmail());

        userRepository.save(user);
    }


    public void updateUserEmail(@NonNull @Valid EmailUpdateRequest request) {
        var currentEmail = request.getCurrentEmail();
        var newEmail = request.getNewEmail();

        if (currentEmail.equals(newEmail) || userRepository.existEmail(newEmail)) {
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


    public List<UserResponse> search(UserSearchRequest request) {
        var pageable = PageRequest.of(request.getPage(), request.getPageSize());
        var predicate = QPredicates.builder()
                .add(request.getName(), QUser.user.name::startsWith)
                .add(request.getDateOfBirth(), QUser.user.dateOfBirth::after)
                .add(request.getPhone().getPhone(), QUser.user.phones.any().phone::eq)
                .add(request.getEmail().getEmail(), QUser.user.emails.any().email::eq)
                .buildOr();

        return userRepository
                .findAll(predicate, pageable)
                .getContent()
                .stream()
                .map(userMapper::toBasicResp)
                .collect(Collectors.toList());
    }


    public User login(@NonNull @Valid LoginRequest request) {
        var email = Email.builder().email(request.getEmail()).build();
        var password = request.getPassword();

        User user = userRepository.findByEmailsContaining(email)
                .orElseThrow(() -> new RuntimeException("*** Ошибка!!! Пользователь с e-mail не найден."));

        if (!user.getPassword().equals(password))
            throw new RuntimeException("*** Неверный пароль!");

        return user;
    }

}
