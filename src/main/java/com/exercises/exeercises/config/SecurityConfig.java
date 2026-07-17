package com.exercises.exeercises.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

        return http
            .cors(Customizer.withDefaults())
            .csrf(customizer -> customizer.disable())
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
                                "/user/**",
                                "/exercise/**",
                                "/team/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
            .formLogin(
                form -> 
                    form.loginPage("/login")
                        .loginProcessingUrl("/login/check")
                        .defaultSuccessUrl("/", true)
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

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
