// package com.smcomanager.Controllers;


// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.smcomanager.Helper.UserDetailHelper;
// import com.smcomanager.SCM_Entity.Users;
// import com.smcomanager.Services.UserService;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;



// @Controller
// @RequestMapping("/user")
// public class UserController {

//     Logger logger=LoggerFactory.getLogger(UserController.class);

//     @Autowired
//     private UserService userService;

//     @ModelAttribute
//     public void addLoggedInUserInformation(Model model,Authentication authentication){

//         String username=UserDetailHelper.getEmailOfLoggedInUser(authentication);

//         Users user= userService.getUserByEmail(username);
//         model.addAttribute("loggedInUser", user);
       
//              logger.info(username); 
//     }

// @GetMapping("/dashboard")
// public String getDashboard() {
   
//     return "user/dashboard";
// }       

// @GetMapping("/profile")
// public String getProfile(Model model,Authentication authentication) {

// // String username=UserDetailHelper.getEmailOfLoggedInUser(authentication);

// //  Users user= userService.getUserByEmail(username);
// //  model.addAttribute("loggedInUser", user);

// //       logger.info(username);    // It check which user name is currently login 
   
//     return "user/profile";

// } 


// }


package com.smcomanager.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            logger.warn("Authentication object is null. No user is logged in.");
            model.addAttribute("loggedInUser", null);
            return;
        }

        String username = UserDetailHelper.getEmailOfLoggedInUser(authentication);
        if (username != null) {
            Users user = userService.getUserByEmail(username);
            model.addAttribute("loggedInUser", user);
            logger.info("Logged in user: {}", username);
        } else {
            logger.warn("Could not retrieve username from authentication.");
            model.addAttribute("loggedInUser", null);
        }
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "user/dashboard"; // Returns the dashboard view
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "user/profile"; // Returns the profile view
    }
}
