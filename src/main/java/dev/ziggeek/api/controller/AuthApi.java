package dev.ziggeek.api.controller;


import dev.ziggeek.api.model.dto.request.LoginRequest;
import dev.ziggeek.api.model.dto.respomse.JwtResponse;
import dev.ziggeek.api.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="API Аутентификаци", description="Аутентификация пользователя на основе e-mail и пароля")
public class AuthApi {

    private final AuthService authService;

    @Operation(
            summary = "Авторизация пользователя",
            description = "Тута входим в систему",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {@ExampleObject(value = "{\n" +
                                    "  \"email\": \"abubakar@gmail.com\",\n" +
                                    "  \"password\": \"kukuruza\"\n" +
                                    "}")}
                    )
            )
    )
    @PostMapping("/login")
    private JwtResponse login(@RequestBody LoginRequest body) {
        return new JwtResponse(authService.login(body));
    }
}
