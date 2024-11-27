// package com.smcomanager.Controllers;

// import org.slf4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.security.core.Authentication;

// import com.smcomanager.Helper.UserDetailHelper;
// import com.smcomanager.SCM_Entity.Users;
// import com.smcomanager.Services.UserService;


// @ControllerAdvice
// public class RootController {

//     private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

//     @Autowired
//     private UserService userService;

//     @ModelAttribute
//     public void addLoggedInUserInformation(Model model, Authentication authentication) {
//         if (authentication == null || authentication.getPrincipal() == null) {
//             return;
//         }

//         String username = UserDetailHelper.getEmailOfLoggedInUser(authentication);
//         logger.info("User logged in: {}", username);

//         // Get user from the database
//         Users user = userService.getUserByEmail(username);

//         if (user != null) {
//             logger.debug("User Details: Name = {}, Email = {}", user.getName(), user.getEmail());
//             model.addAttribute("loggedInUser", user);
//         } else {
//             logger.warn("User not found for email: {}", username);
//             // Optionally handle the case when user is not found (e.g., redirect to an error page)
//         }
//     }
// }

