package com.team_3.School_Medical_Management_System.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/medical-events/emergency").permitAll()
                        .requestMatchers("/api/excel/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Chỉ /api/auth/** được phép truy cập mà không cần token
                        .requestMatchers("/api/vaccine_types/**").hasAnyAuthority("ROLE_NURSE", "ROLE_ADMIN")
                        .requestMatchers("/api/vaccinebatches/**").hasAnyAuthority("ROLE_NURSE", "ROLE_ADMIN") // Cho phép NURSE và ADMIN
                        .requestMatchers("/api/vaccination_records/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT") // Chỉ NURSE và PHỤ HUYNH
                        .requestMatchers("/api/Consent_forms/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/StudentHealthProfiles/**").hasAnyAuthority("ROLE_PARENT")
                        .requestMatchers("/api/health-check/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/health-check-schedule/**").hasAnyAuthority("ROLE_NURSE", "ROLE_ADMIN")
                        .requestMatchers("/api/health-check-result/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/health-consent/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/medical-events/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/medical-submission/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .requestMatchers("/api/medical-confirmations/**").hasAnyAuthority("ROLE_NURSE", "ROLE_PARENT")
                        .anyRequest().authenticated() // Tất cả request khác (bao gồm /api/** khác và không phải /api) cần token
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
