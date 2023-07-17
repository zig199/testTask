package dev.ziggeek.api.security;


import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.request.LoginRequest;
import dev.ziggeek.api.model.entity.User;
import dev.ziggeek.api.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;


@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public void authenticate(HttpServletRequest request, HttpServletResponse response) throws UsernameNotFoundException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        var userId = JwtUtil.getUserId(authorizationHeader);

        if (userId != null) {
            User user = userService.findById(userId);

            UserDetails details = new UserSecuirity(
                    user.getId(),
                    user.getPassword(),
                    user.getName(),
                    Collections.singleton(new SimpleGrantedAuthority("USER")));
            Authentication auth = new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }


    public String login(@NonNull @Valid LoginRequest request) {
        var email = Email.builder().email(request.getEmail()).build();
        var password = request.getPassword();

        User user = userService.findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("*** Неверный пароль!");

        return JwtUtil.generateJwtToken(user.getId());
    }


    public Long getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authenication = context.getAuthentication();
        UserSecuirity details = (UserSecuirity)authenication.getPrincipal();
        if (details == null)
            throw new RuntimeException("UserDetails is Empty");

        return details.getId();
    }
}
