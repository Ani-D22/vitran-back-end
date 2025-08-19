package com.vitran.backend.config;

import com.vitran.backend.security.JwtAuthFilter;
import com.vitran.backend.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT service config from properties
    @Bean
    public JwtTokenService jwtTokenService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.issuer:vitran-backend}") String issuer,
            @Value("${security.jwt.access-token-ttl-seconds:900}") long accessTtlSeconds
    ) {
        return new JwtTokenService(secret, issuer, accessTtlSeconds);
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtTokenService tokenService) {
        return new JwtAuthFilter(
                tokenService,
                Set.of(
                        "/api/auth/login",
                        "/api/swagger-ui/**",
                        "/api/v3/api-docs/**",
                        "/api/actuator/**"
                )
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(h -> h.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        // everything else secured
                        .anyRequest().authenticated()
                )
                // ensure your JWT filter is before basic auth filter
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class)
                .sessionManagement(sm -> sm.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                ))
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return http.build();
    }
}

