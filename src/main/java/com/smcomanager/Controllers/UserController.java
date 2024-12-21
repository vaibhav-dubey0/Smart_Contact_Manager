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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Helper.Message;
import com.smcomanager.Helper.MessageType;
import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.DirectMessageService;
import com.smcomanager.Services.FeedbackService;
import com.smcomanager.Services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
   
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private DirectMessageService directMessageService;

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

    @GetMapping("/do-logout")
    public String getLogout() {
        return "login";
    }
    

     @GetMapping("/feedback")
    public String getFeedback(Model model,Authentication authentication) {

        return "user/feedback";  // Returns the feedback.html page
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam String name, 
                                 @RequestParam String email, 
                                 @RequestParam String message, 
                                 Model model,HttpSession session) {


                                    boolean isSaved = feedbackService.saveFeedback(name, email, message);

        if (isSaved) {

            session.setAttribute("message", Message.builder()
            .content("Thankyou For Your Feedback ")
            .type(MessageType.RED)
            .build());

        } else {
            session.setAttribute("message", Message.builder()
                    .content("Please Enter Valid Details  ")
                    .type(MessageType.RED)
                    .build());
        }
       
        logger.info("Received feedback from user: {}", name);
          
        return "user/feedback";  // You can return to the feedback page or a confirmation page
    }


    // @RequestMapping("/directmessage")
    // public String showFeedbackForm() {
        
    //     return "user/direct_massege";
    // }

    // @PostMapping("/directmessage")
    // public String submitFeedback(@RequestParam("email") String email,
    //                              @RequestParam("message") String message,
    //                              Model model,HttpSession session) {

    //                                 boolean isSent = directMessageService.sendDirectMessage(email, message);

    //                                 if (isSent) {
                            
    //                                     session.setAttribute("message", Message.builder()
    //                                     .content("Thankyou For Your Feedback ")
    //                                     .type(MessageType.RED)
    //                                     .build());
                            
    //                                 } else {
    //                                     session.setAttribute("message", Message.builder()
    //                                             .content("Please Enter Valid Details  ")
    //                                             .type(MessageType.RED)
    //                                             .build());
    //                                 }
        
           

    //     return "user/direct_massege";
    // }

   
}
