package com.smcomanager.Helper;


import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class UserDetailHelper {

    public static String getEmailOfLoginUser(Authentication authention){

        Principal principal=(Principal)authention.getPrincipal();

        if(principal instanceof OAuth2AuthenticatedPrincipal){

            var OAuth2AuthenticationToken=(OAuth2AuthenticationToken)authention;
            var clintId=OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            if(clintId.equalsIgnoreCase("google")){

            }

            elseif{

            }

             return "";  

        }

        else{

            return principal.getName();
        }

    }
    
}
