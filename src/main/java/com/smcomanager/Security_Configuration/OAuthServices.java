package com.smcomanager.Security_Configuration;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.smcomanager.Helper.AppConstent;
import com.smcomanager.Repository.UserRepo;
import com.smcomanager.SCM_Entity.Providers;
import com.smcomanager.SCM_Entity.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuthServices implements AuthenticationSuccessHandler {

    Logger logger=LoggerFactory.getLogger(OAuthServices.class);
   
    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                 logger.info("OAuthAuthenicationSuccessHandler");
                 response.sendRedirect("/user/profile");

        // identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);
         var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        Users user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstent.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnable(true);
        user.setPassword("dummy");
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            // google
            // google attributes
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            // github
            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);

            user.setAbout("This account is created using github");
        }

        else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
        }

        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        Users user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }
   
    }

    
}
