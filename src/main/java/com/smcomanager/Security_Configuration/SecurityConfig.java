package com.smcomanager.Security_Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.smcomanager.Services.Impl.SecurityCoustemUserDetali;

@Configuration
public class SecurityConfig {
   
    @Autowired
    private SecurityCoustemUserDetali coustemUserDetali;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configuring BCryptPasswordEncoder
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     PasswordEncoder encoder = passwordEncoder(); // Use the encoder here

    //     UserDetails user = User.withUsername("Vaibhav")
    //                            .password(encoder.encode("Vaibhav@123")) // Encode the password
    //                            .roles("USER") // Assign roles if required
    //                            .build();

    //     return new InMemoryUserDetailsManager(user);
    // }


    @Bean
    public DaoAuthenticationProvider authProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(coustemUserDetali);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }
}

