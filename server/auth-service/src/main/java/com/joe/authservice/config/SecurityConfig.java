package com.joe.authservice.config;

import com.joe.authservice.helpers.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    @Value("${roles.teacher-role}")
    private String teacherRole;

    @Value("${roles.student-role}")
    private String studentRole;

    @Value("${authorize-ep.auth-controller.signup}")
    private String signupEndpoint;

    @Value("${authorize-ep.auth-controller.login}")
    private String loginEndpoint;

    @Value("${authorize-ep.auth-controller.logout}")
    private String logoutEndpoint;


    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        http.oauth2ResourceServer(oauth2ResourceServer -> {
            oauth2ResourceServer.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(jwtAuthConverter);
            });
        });

        http.authorizeHttpRequests(authorizeHttpRequest -> {
            authorizeHttpRequest
                    .requestMatchers(signupEndpoint).permitAll()
                    .requestMatchers(loginEndpoint).permitAll()
                    .requestMatchers(logoutEndpoint).hasAnyRole(studentRole, teacherRole)
                    .anyRequest()
                    .authenticated();
        });

        http.sessionManagement(sessionManagement -> {
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }
}
