package com.example.backend.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Add this line
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend.Security.CustomUserDetailsService;
import com.example.backend.Security.JwtAuthenticationFilter;

import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)  //by default csrf is enabled, it blocks post requests
        .authorizeHttpRequests(registry ->{
            registry.requestMatchers("/staff/**").hasRole("ADMIN");
            // registry.requestMatchers("/staff/**").hasAnyRole("ADMIN", "USER");
            registry.requestMatchers("/customer/**").hasRole("ADMIN");
            registry.requestMatchers("/calls/**").hasRole("ADMIN");
            registry.requestMatchers("/admin/findByUsername/{username}").hasRole("ADMIN");

            registry.requestMatchers(
                "/admin/authenticate", 
                            "/admin/register",
                            "/admin/checkIfExist/{username}")
                            .permitAll();
            // registry.requestMatchers("/admin/authenticate").permitAll();
            // registry.requestMatchers("/**").permitAll();
            registry.anyRequest().authenticated();
        })
        .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
