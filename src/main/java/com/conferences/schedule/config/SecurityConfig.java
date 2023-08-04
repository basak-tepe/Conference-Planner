package com.conferences.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AUTHENTICATION
    @Bean
    //users we hardcoded
    //equivalent of the configure method in spring boot 2
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("basak")
                .password(encoder.encode("basak"))
                .roles("ADMIN")
                .build();


        UserDetails user = User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    //AUTHORIZATION

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/events/events").permitAll()
                        .requestMatchers("/api/events/login").permitAll()
                        .requestMatchers("/api/events/files/download/*").permitAll()
                        .requestMatchers("/api/events/files/add").permitAll()
                        .anyRequest().authenticated());
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

    //password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
