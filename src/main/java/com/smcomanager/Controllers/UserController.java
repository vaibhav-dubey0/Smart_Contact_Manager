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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Form_Handler.FeedbackForm;
import com.smcomanager.Form_Handler.MessageForm;
import com.smcomanager.Helper.Message;
import com.smcomanager.Helper.MessageType;
import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.DirectMessageService;
import com.smcomanager.Services.FeedbackService;
import com.smcomanager.Services.UserService;

import jakarta.servlet.http.HttpSession;


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
        model.addAttribute("feedbackForm", new FeedbackForm());
        return "user/feedback";  // Returns the feedback.html page
    }

    @PostMapping("/feedback")
    public String submitFeedback(@Validated FeedbackForm feedbackForm, BindingResult result, 
                                 Model model,HttpSession session) {

                                    if(result.hasErrors()){
                                        result.getAllErrors().forEach(error -> logger.info(error.toString()));
                                         
                                        session.setAttribute("message", Message.builder()
                                                .content("Please correct the following errors")
                                                .type(MessageType.RED)
                                                .build());
                            
                                                return "user/feedback";
                                     }

        boolean isSaved = feedbackService.saveFeedback(feedbackForm.getName(), feedbackForm.getEmail(), feedbackForm.getMessage());

        if (isSaved) {

            session.setAttribute("message", Message.builder()
            .content("Thankyou For Your Feedback ")
            .type(MessageType.RED)
            .build());
        } 
       
        logger.info("Received feedback from user: {}", feedbackForm.getName()); 
        return "user/feedback";  // You can return to the feedback page or a confirmation page
    }


    @RequestMapping("/directmessage")
    public String showFeedbackForm(Model model) {
        model.addAttribute("messageForm", new MessageForm());
        return "user/direct_massege";
    }

    @PostMapping("/directmessage")
    public String submitFeedback(@Validated MessageForm messageForm, BindingResult result, 
                                 Model model,HttpSession session) {
                                    if(result.hasErrors()){
                                        result.getAllErrors().forEach(error -> logger.info(error.toString()));
                                         
                                        session.setAttribute("message", Message.builder()
                                                .content("Please correct the following errors")
                                                .type(MessageType.RED)
                                                .build());
                            
                                                return "user/direct_massege";
                                     }

                                    boolean isSent = directMessageService.sendDirectMessage(messageForm.getEmail(), messageForm.getMessage());

                                    if (isSent) {
                            
                                        session.setAttribute("message", Message.builder()
                                        .content("Thankyou For Your Feedback ")
                                        .type(MessageType.RED)
                                        .build());
                            
                                    }
                                    
        return "user/direct_massege";
    }

   
}
