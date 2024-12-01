package com.smcomanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smcomanager.Form_Handler.ContactForm;
import com.smcomanager.Helper.Message;
import com.smcomanager.Helper.MessageType;
import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.ContactService;
import com.smcomanager.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
   
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addContactView(Model model){

        // Model attribute use to recieve data from form in blank 
  ContactForm contactForm=new ContactForm();
  contactForm.setFavorite(true);
  model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@ModelAttribute ContactForm contactForm,Authentication authentication,HttpSession session){

         String userName=UserDetailHelper.getEmailOfLoggedInUser(authentication);
           
        Contact contact=new Contact();

         Users users=userService.getUserByEmail(userName);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(users);

        contactService.save(contact);

         System.out.println(contact);

         session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.GREEN)
                        .build());

        return "redirect:/user/contacts/add";

    }
    
}
