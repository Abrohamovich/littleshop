package org.abrohamovich.littleshop.infrustructure.config;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.infrustructure.security.JwtAuthenticationEntryPoint;
import org.abrohamovich.littleshop.infrustructure.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/auth/**").permitAll()
                        .requestMatchers("api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/categories/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/customers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/suppliers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/offers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/orders/{id}").hasRole("ADMIN")
                        .requestMatchers("api/v1/categories/**").hasAnyRole("ADMIN", "WORKER")
                        .requestMatchers("api/v1/customers/**").hasAnyRole("ADMIN", "WORKER")
                        .requestMatchers("api/v1/suppliers/**").hasAnyRole("ADMIN", "WORKER")
                        .requestMatchers("api/v1/offers/**").hasAnyRole("ADMIN", "WORKER")
                        .requestMatchers("api/v1/orders/**").hasAnyRole("ADMIN", "WORKER")
                        .anyRequest().authenticated())
                .exceptionHandling(cfg -> cfg
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
