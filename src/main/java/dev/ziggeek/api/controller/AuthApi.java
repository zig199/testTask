package dev.ziggeek.api.controller;


import dev.ziggeek.api.model.dto.respomse.JwtResponse;
import dev.ziggeek.api.model.dto.request.LoginRequest;
import dev.ziggeek.api.service.security.JwtProvider;
import dev.ziggeek.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final UserService userService;
    private final JwtProvider jwtProvider;


    @PostMapping("/login")
    private JwtResponse login(@RequestBody LoginRequest body) {
        var user = userService.login(body);
        var jwt = jwtProvider.generate(user.getId());
        return new JwtResponse(jwt);
    }
}
