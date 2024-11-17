package com.smcomanager.Controllers;

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
public String getProfile() {
   
    return "user/profile";

} 


}
