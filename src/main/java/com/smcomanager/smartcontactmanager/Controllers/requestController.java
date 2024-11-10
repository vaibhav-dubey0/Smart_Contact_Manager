package com.smcomanager.smartcontactmanager.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class requestController {

    @GetMapping("/register")
public String getSignup(){
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


}
