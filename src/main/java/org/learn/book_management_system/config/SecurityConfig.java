package org.learn.book_management_system.config;

import lombok.RequiredArgsConstructor;
import org.learn.book_management_system.user.CustomUserDetailsService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableCaching
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        String[] permitAll = {
                "/css/**",
                "/js/**",
                "/font/**",
                "/image/**"
        };

        return http.authorizeHttpRequests(auth -> auth.requestMatchers(permitAll).permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .formLogin(login -> login.loginProcessingUrl("/authenticate")
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                ).logout(logout -> logout.logoutUrl("/logout")
                        .permitAll()
                        .logoutSuccessUrl("/login?logout=true")
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
