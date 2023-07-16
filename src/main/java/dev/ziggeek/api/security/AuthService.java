package dev.ziggeek.api.service.security;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ThreadLocal<Long> current = new ThreadLocal<>();
    private final JwtProvider jwtProvider;


    public void authenticate(@NonNull HttpServletRequest reqt, @NonNull HttpServletResponse resp) {
        String authorizationHeader = reqt.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authorizationHeader)) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new RuntimeException("MISSING AUTHORIZATION HEADER");
        }

        try {
            var userId = jwtProvider.getUserId(authorizationHeader);

            if (userId != null) {
                log.info("Current thread {}: user {}", Thread.currentThread().getName(), userId);
                setCurrent(userId);
            } else {
                log.info("Current thread {}: user {} is not found", Thread.currentThread().getName(), authorizationHeader);
            }

        } catch (Exception e) {
            log.warn("Unexpected error occurred : {}", e.getMessage());
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public Long getCurrent() {
        var temp = current.get();

        if (temp != null)
            return temp;

        return 0L;
    }

    public void clearCurrent() {
        current.remove();
    }

    public void setCurrent(@NonNull Long userId) {
        MDC.put("user.id", String.valueOf(userId));
        log.info("Установлен пользователь с ID: {}", userId);
        current.set(userId);
    }
}
