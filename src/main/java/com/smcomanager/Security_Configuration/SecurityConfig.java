package com.smcomanager.Security_Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.smcomanager.Services.Impl.SecurityCoustemUserDetali;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCoustemUserDetali coustemUserDetali;

    @Autowired
    private OAuthServices handler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/user/**").authenticated(); // Corrected the matcher syntax
                auth.anyRequest().permitAll();
            })
            .formLogin(formLogin -> {
                formLogin.loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/user/profile", true) // Ensures this URL is always used after login
                       // .failureUrl("/login?error=true") // Uncommented and corrected failure URL
                        .usernameParameter("email")
                        .passwordParameter("password");
            })
            .oauth2Login(oauth -> {
                oauth.loginPage("/login");
                oauth.successHandler(handler);
            })
            .logout(logout -> {
                logout.logoutUrl("/do-logout")
                      .logoutSuccessUrl("/login?logout=true");
            });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(coustemUserDetali);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}


