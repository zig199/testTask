package dev.ziggeek.api.config;


import dev.ziggeek.api.security.AuthService;
import dev.ziggeek.api.security.JwtConfigurer;
import dev.ziggeek.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(authService()));
    }


    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }


    @Bean
    protected AuthService authService() {
        return new AuthService(userService, passwordEncoder());
    }
}