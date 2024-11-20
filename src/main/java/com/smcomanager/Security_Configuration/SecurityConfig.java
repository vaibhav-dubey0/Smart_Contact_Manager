package com.smcomanager.Security_Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.smcomanager.Services.Impl.SecurityCoustemUserDetali;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCoustemUserDetali coustemUserDetali;
   
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/user/**").authenticated();
                auth.anyRequest().permitAll();
            })
            .formLogin(formLogin -> {
                formLogin.loginPage("/login")
                         .loginProcessingUrl("/authenticate")
                         .defaultSuccessUrl("/user/profile", true)
                        // .failureUrl("/login?error=true")
                         .usernameParameter("email")
                         .passwordParameter("password");

                     //    formLogin.failureHandler(authFailtureHandler);
            })
            .logout(logout -> {
                logout.logoutUrl("/do-logout")
                      .logoutSuccessUrl("/login?logout=true")
                      .deleteCookies("JSESSIONID")
                      .invalidateHttpSession(true);
            });
        
         
          // Oauth2 or google lofin 

          httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login");
             oauth.successHandler(handler);
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


