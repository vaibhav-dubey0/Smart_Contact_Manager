package com.smcomanager.Controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/user")
public class UserController {

@GetMapping("/dashboard")
public String getDashboard() {
   
    return "user/dashboard";
}       

@GetMapping("/profile")
public String getProfile(Authentication authentication) {

Logger logger=LoggerFactory.getLogger(UserController.class);

   
    return "user/profile";

} 


}
