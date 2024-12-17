package com.smcomanager.Security_Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.smcomanager.Services.Impl.SecurityCoustemUserDetali;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCoustemUserDetali coustemUserDetali;

    @Autowired
    private OAuthServices handler;

    @Autowired
    private AuthFailtureHandler authFailtureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/**").authenticated(); // Corrected the matcher syntax
                    auth.anyRequest().permitAll();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login")
                            .loginProcessingUrl("/authenticate")
                            .defaultSuccessUrl("/user/profile", true) // Ensures this URL is always used after login
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .failureHandler(authFailtureHandler);
                })
                .oauth2Login(oauth -> {
                    oauth.loginPage("/login");
                    oauth.successHandler(handler);
                })
                .logout(logout -> {
                    logout.logoutUrl("/do-logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll();
                    ;
                })
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/do-logout", "GET")));

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(coustemUserDetali);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}





