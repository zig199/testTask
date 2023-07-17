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
import dev.ziggeek.api.security.AuthService;
import dev.ziggeek.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name="Пользовательский API", description="Выполнение оперций с данными пользователя")
public class UserApi {

    private final AccountService accountService;
    private final UserService userService;
    private final AuthService authService;


    private void checkAccess(@NonNull Long userId) {
        var current = authService.getCurrentUserId();
        if (!Objects.equals(current, userId))
            throw new RuntimeException("НЕТ ДОСТУПА ДЛЯ ВЫПОЛНЕНИЯ ДАННОЙ ОПЕРАЦИИ");
    }


    @Operation(
            summary = "Поиск юзеров",
            description = "Ищет пользователей по заданному фильтру",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {@ExampleObject(value = "{\n" +
                                    "  \"name\": \"Abubakar\",\n" +
                                    "  \"dateOfBirth\": \"1999-08-20\",\n" +
                                    "  \"phone\": {\n" +
                                    "    \"phone\": \"89500009999\"\n" +
                                    "  },\n" +
                                    "  \"email\": {\n" +
                                    "    \"email\": \"abubakar@gmail.com\"\n" +
                                    "  }\n" +
                                    "}")}
                    )
            )
    )
    @PostMapping("/search")
    public List<UserResponse> search(@RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                     @RequestBody UserSearchRequest userSearchRequest) {
        return userService.search(userSearchRequest, page, pageSize);
    }


    @PutMapping("/add/email")
    public void addUserEmail(@RequestBody EmailRequest request) {
        checkAccess(request.getUserId());
        userService.addUserEmail(request);
    }


    @DeleteMapping("/remove/email")
    public void removeUserEmail(@RequestBody EmailRequest request) {
        checkAccess(request.getUserId());
        userService.removeUserEmail(request);
    }


    @PutMapping("/update/email")
    public void updateUserEmail(@RequestBody EmailUpdateRequest request) {
        checkAccess(request.getUserId());
        userService.updateUserEmail(request);
    }


    @PutMapping("/add/phone")
    public void addUserPhone(@RequestBody PhoneRequest request) {
        checkAccess(request.getUserId());
        userService.addUserPhone(request);
    }


    @DeleteMapping("/remove/phone")
    public void removeUserPhone(@RequestBody PhoneRequest request) {
        checkAccess(request.getUserId());
        userService.removeUserPhone(request);
    }


    @PutMapping("/update/phone")
    public void updateUserPhone(@RequestBody PhoneUpdateRequest reqt) {
        checkAccess(reqt.getUserId());
        userService.updateUserPhone(reqt);
    }

    @Operation(
            summary = "Перевод денег",
            description = "Api для совершения транзакции по переводу денег другому пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {@ExampleObject(value = "{\n" +
                                    "  \"fromUser\": 1,\n" +
                                    "  \"toUser\": 2,\n" +
                                    "  \"amount\": 100\n" +
                                    "}")}
                    )
            )
    )
    @PostMapping("/transfer")
    public void transfer(@RequestBody AccountTransferRequest request) {
        checkAccess(request.getFromUser());
        accountService.transfer(request);
    }
}
