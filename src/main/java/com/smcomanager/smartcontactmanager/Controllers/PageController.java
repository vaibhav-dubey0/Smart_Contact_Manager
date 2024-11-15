package com.smcomanager.smartcontactmanager.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smcomanager.Services.UserServices;
import com.smcomanager.smartcontactmanager.Form_Handler.UserForm;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PageController {
  
    @Autowired
    UserServices userServices;

@GetMapping("/register")
    public String getSignup(Model model){
        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);
      
        return "register";
}

 @GetMapping("/")
public String getMethodName() {
    return "home";
}


@GetMapping("/about")
public String getAbouut(){
    return "about";
}
    
@GetMapping("/services")
public String getServices(){
    return "services";
}

@GetMapping("/contact")
public String getContact(){
    return "contact";
}

   @PostMapping("/do-register")
   public String postMethodName(@ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.print(userForm.getName());
        System.out.print(userForm);

   
        User user=User.builder()
        .name(userForm.getName())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .email(userForm.getEmail())
        .phoneNumber(userForm.getPhoneNumber())
        .build();


         
       



        
      User saveUser=userServices.saveUser(user);

       return "redirect:register";
   }
   

}
