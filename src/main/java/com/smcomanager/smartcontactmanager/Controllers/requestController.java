package com.smcomanager.smartcontactmanager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class requestController {

@GetMapping("/")
public String getMethodName() {
    return "index";
}

    
}
