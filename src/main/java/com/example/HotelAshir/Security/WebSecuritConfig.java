package com.example.HotelAshir.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecuritConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and().authorizeHttpRequests()
                .antMatchers(HttpMethod.GET).hasAuthority("read")
                .antMatchers(HttpMethod.POST).hasAuthority("write")
                .antMatchers(HttpMethod.PATCH).hasAuthority("write")
                .antMatchers(HttpMethod.DELETE).hasAuthority("write")
                .and()
                .csrf().disable()
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(passwordEncoder().encode("password"))
                        .authorities("read")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .authorities("read","write")
                        .build()
        );
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}