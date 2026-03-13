package com.edu.politecnicointernacional.sistema_gestion_zoologico.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.GET, "/api/animales/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/animales").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/animales/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/animales/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/api/alimentaciones/**").hasAnyRole("ADMIN", "VETERINARIO", "CUIDADOR")
                        .requestMatchers(HttpMethod.POST, "/api/alimentaciones").hasAnyRole("ADMIN", "CUIDADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/alimentaciones/**").hasAnyRole("ADMIN", "CUIDADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/alimentaciones/**").hasRole("ADMIN")

                        .requestMatchers("/api/citas/**").hasAnyRole("ADMIN", "VETERINARIO")

                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic
                        .realmName("Zoo Management System")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails veterinario = User.builder()
                .username("veterinario")
                .password(passwordEncoder().encode("vet12345"))
                .roles("VETERINARIO")
                .build();

        UserDetails cuidador = User.builder()
                .username("cuidador")
                .password(passwordEncoder().encode("cuid1234"))
                .roles("CUIDADOR")
                .build();

        return new InMemoryUserDetailsManager(admin, veterinario, cuidador);
    }
}
