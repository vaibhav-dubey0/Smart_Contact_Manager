package com.smcomanager.Security_Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configuring BCryptPasswordEncoder
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder(); // Use the encoder here

        UserDetails user = User.withUsername("Vaibhav")
                               .password(encoder.encode("Vaibhav@123")) // Encode the password
                               .roles("USER") // Assign roles if required
                               .build();

        return new InMemoryUserDetailsManager(user);
    }
}

