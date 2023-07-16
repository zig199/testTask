package dev.ziggeek.api.controller;


import java.util.List;
import java.util.Objects;
import dev.ziggeek.api.model.dto.request.AccountTransferRequest;
import dev.ziggeek.api.model.dto.email.EmailRequest;
import dev.ziggeek.api.model.dto.email.EmailUpdateRequest;
import dev.ziggeek.api.model.dto.phone.PhoneRequest;
import dev.ziggeek.api.model.dto.phone.PhoneUpdateRequest;
import dev.ziggeek.api.model.dto.request.UserSearchRequest;
import dev.ziggeek.api.model.dto.respomse.UserResponse;
import dev.ziggeek.api.service.AccountService;
import dev.ziggeek.api.service.security.AuthService;
import dev.ziggeek.api.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final AccountService accountService;
    private final UserService userService;
    private final AuthService authService;


    private void checkAccess(@NonNull Long userId) {
        var current = authService.getCurrent();
        if (!Objects.equals(current, userId))
            throw new RuntimeException("НЕТ ДОСТУПА ДЛЯ ВЫПОЛНЕНИЯ ДАННОЙ ОПЕРАЦИИ");
    }


    @PostMapping("/search")
    public List<UserResponse> search(@RequestBody UserSearchRequest userSearchRequest) {
        return userService.search(userSearchRequest);
    }



    @PutMapping("/add/email")
    public void addUserEmail(@RequestBody EmailRequest request) {
//        checkAccess(request.getUserId());
        userService.addUserEmail(request);
    }


    @DeleteMapping("/remove/email")
    public void removeUserEmail(@RequestBody EmailRequest request) {
//        checkAccess(request.getUserId());
        userService.removeUserEmail(request);
    }


    @PutMapping("/update/email")
    public void updateUserEmail(@RequestBody EmailUpdateRequest request) {
//        checkAccess(request.getUserId());
        userService.updateUserEmail(request);
    }


    @PutMapping("/add/phone")
    public void addUserPhone(@RequestBody PhoneRequest request) {
//        checkAccess(request.getUserId());
        userService.addUserPhone(request);
    }


    @DeleteMapping("/remove/phone")
    public void removeUserPhone(@RequestBody PhoneRequest request) {
//        checkAccess(request.getUserId());
        userService.removeUserPhone(request);
    }


    @PutMapping("/update/phone")
    public void updateUserPhone(@RequestBody PhoneUpdateRequest reqt) {
//        checkAccess(reqt.getUserId());
        userService.updateUserPhone(reqt);
    }


    @PostMapping("/transfer")
    public void transfer(@RequestBody AccountTransferRequest request) {
//        checkAccess(request.getFromUser());
        accountService.transfer(request);
    }
}
