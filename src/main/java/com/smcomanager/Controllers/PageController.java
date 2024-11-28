package com.smcomanager.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smcomanager.Form_Handler.UserForm;
import com.smcomanager.Helper.Message;
import com.smcomanager.Helper.MessageType;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {
  
    
    @Autowired
    private UserService userService;
   


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

@GetMapping("/home")
public String getHome() {
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

@GetMapping("/login")
public String loginPage(){
    return "login";
}

@GetMapping("/contact")
public String getContact(){
    return "contact";
}

@PostMapping("/do-logout")
public String postMethodName() {
    return "redirect:login";
}


   @PostMapping("/do-register")
   public String postMethodName(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult,HttpSession session) {
        System.out.print(userForm.getName());
        System.out.print(userForm);

        if(bindingResult.hasErrors()){
            return "register";
        }

        Users user = new Users();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        // user.setEnabled(false);
        user.setProfilePic(
                "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");

        Users savedUser = userService.saveUser(user);

        System.out.println("user saved :");

        // message = "Registration Successful"

        Message message=Message.builder().content("Registration Successful").type(MessageType.RED).build();

         session.setAttribute("message", message);



       return "redirect:register";
   }
   

}
