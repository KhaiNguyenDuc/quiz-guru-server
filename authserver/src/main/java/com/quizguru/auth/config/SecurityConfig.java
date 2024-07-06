package com.quizguru.auth.config;

import com.quizguru.auth.filter.AuthenticationFilter;
import com.quizguru.auth.jwt.JwtAuthenticationEntryPoint;
import com.quizguru.auth.jwt.JwtTokenProvider;
import com.quizguru.auth.service.impl.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider tokenProvider;
    private final SecurityUserService securityUserService;

    private final String[] ALLOW_URL = {
            "/auth/api/v1/**",
    };

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(tokenProvider, securityUserService);
    }

    @Bean
    JwtAuthenticationEntryPoint unauthorizedHandler(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                 csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler()))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(ALLOW_URL).permitAll()
//                        .requestMatchers("/auth/api/v1/refresh-token").authenticated()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
