package com.example.kunuz.config.security;

import com.example.kunuz.config.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        // authentication -> login,password to'g'rimi , user activmi?
//        UUID password = UUID.randomUUID();
//        System.out.println("Mazgi : " + password);
//
//        UserDetails userDetails = User.builder().
//                username("user").
//                password("{noop}" + password).
//                roles("USER").build();
//
//        UserDetails adminDetails = User.builder().
//                username("admin").
//                password("{noop}" + 123).
//                roles("ADMIN").build();
//
//        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(userDetails, adminDetails));
//        return authenticationProvider;
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    private PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization

        http.csrf().disable().cors().disable().authorizeRequests()
                .requestMatchers("/article/admin/create", "/article/admin/update/{id}", "/article/admin/delete/{id}").hasRole("MODERATOR")
                .requestMatchers("/article/change/{id}").hasRole("PUBLISHER")
                .requestMatchers("/article/user/**").permitAll()
                .requestMatchers("/article_type/**","/attach/get","/comment/sec/get","/email_history/**").hasRole("ADMIN")
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/comment/public/**").permitAll()
                .requestMatchers("/profile/user/**").hasRole("USER")
                .requestMatchers("/profile/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/init/admin").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();

        return http.build();
    }

}
