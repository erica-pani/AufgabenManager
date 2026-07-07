package com.exercises.exeercises.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.exercises.exeercises.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final int STRENGTH = 10;

    private final MyUserDetailsService userDetailsService;

    SecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(
                    request -> 
                        request
                            .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/login",
                                "/login/failed",
                                "/login/check",
                                "/exercise/**",
                                "/user/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
            .formLogin(
                form -> 
                    form.defaultSuccessUrl("/", true)
                        .failureUrl("/login/failed")
                        .permitAll())
            .logout(logout -> logout.logoutUrl("/logout").permitAll())
            .authenticationProvider(authenticationProvider())
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(STRENGTH));
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }
}
