package com.smcomanager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Form_Handler.ContactForm;
import com.smcomanager.Helper.MessageType;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @GetMapping("/add")
    public String addContactView(Model model){

        // Model attribute use to recieve data from form in blank 
  ContactForm contactForm=new ContactForm();
  contactForm.setFavorite(true);
  model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(){


        

        //  session.setAttribute("message",
        //         Message.builder()
        //                 .content("You have successfully added a new contact")
        //                 .type(MessageType.green)
        //                 .build());

        return "redirect:/user/contacts/add";

    }
    
}
