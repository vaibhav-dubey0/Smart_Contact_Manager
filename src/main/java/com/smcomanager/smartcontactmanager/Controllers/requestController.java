package com.smcomanager.smartcontactmanager.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smcomanager.smartcontactmanager.Form_Handler.UserForm;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class requestController {

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
   public String postMethodName(@ModelAttribute UserForm userForm) {
        System.out.print(userForm.getName());
        System.out.print(userForm);
       return "redirect:register";
   }
   

}
