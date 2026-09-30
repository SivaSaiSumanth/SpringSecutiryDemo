package com.springsecurity.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;*/
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello").authenticated()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/payments").hasAuthority("PAYMENT_READ")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Commenting as DB authentication is introduced
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .authorities("PAYMENT_READ")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .authorities("PAYMENT_READ", "PAYMENT_CREATE", "PAYMENT_DELETE")
                .build();

        UserDetails paymentUser = User.builder()
                .username("payment")
                .password(passwordEncoder.encode("payment123"))
                .authorities("PAYMENT_CREATE")
                .build();

        return new InMemoryUserDetailsManager(user, admin, paymentUser);
    }*/
}