package com.smcomanager.Controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.UserService;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

@GetMapping("/dashboard")
public String getDashboard() {
   
    return "user/dashboard";
}       

@GetMapping("/profile")
public String getProfile(Model model,Authentication authentication) {

String username=UserDetailHelper.getEmailOfLoggedInUser(authentication);

 Users user= userService.getUserByEmail(username);
 model.addAttribute("loggedInUser", user);

      logger.info(username);    // It check which user name is currently login 
   
    return "user/profile";

} 


}
