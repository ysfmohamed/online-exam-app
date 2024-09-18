package com.joe.examengineservice.config;

import com.joe.examengineservice.helper.JwtAuthConverter;
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
    @Value("${authorize-ep.exam-controller.create-exam}")
    private String createExamEndpoint;

    @Value("${authorize-ep.exam-controller.assign-exam-to-student}")
    private String assignExamToStudentEndpoint;

    @Value("${authorize-ep.exam-controller.fetch-exam-definitions}")
    private String fetchExamDefinitionsEndpoint;

    @Value("${authorize-ep.exam-controller.fetch-exam-definitions-amount}")
    private String fetchExamDefinitionsAmountEndpoint;

    @Value("${authorize-ep.exam-controller.fetch-exam-instance}")
    private String fetchExamInstanceEndpoint;

    @Value("${authorize-ep.exam-controller.fetch-exam-definition}")
    private String fetchExamDefinitionEndpoint;

    @Value("${authorize-ep.exam-controller.next}")
    private String nextEndpoint;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        http.oauth2ResourceServer(oauth2ResourceServer -> {
            oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
        });

        http.authorizeHttpRequests(authorizeHttpRequests -> {
            authorizeHttpRequests
                    .requestMatchers(fetchExamInstanceEndpoint).permitAll()
                    .requestMatchers(fetchExamDefinitionEndpoint).permitAll()
                    .requestMatchers(createExamEndpoint).hasRole(teacherRole)
                    .requestMatchers(assignExamToStudentEndpoint).hasRole(teacherRole)
                    .requestMatchers(fetchExamDefinitionsEndpoint).hasRole(teacherRole)
                    .requestMatchers(fetchExamDefinitionsAmountEndpoint).hasRole(teacherRole)
                    .requestMatchers(nextEndpoint).hasRole(studentRole)
                    .anyRequest()
                    .authenticated();
        });

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
