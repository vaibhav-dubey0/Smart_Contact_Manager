package com.smcomanager.Controllers;

import org.springframework.validation.BindingResult;

import java.util.UUID;

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

import com.smcomanager.Form_Handler.ContactForm;
import com.smcomanager.Helper.Message;
import com.smcomanager.Helper.MessageType;
import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.ContactService;
import com.smcomanager.Services.ImageService;
import com.smcomanager.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
   
    @Autowired
    private ImageService imageService;
    
    private Logger logger=LoggerFactory.getLogger(ContactController.class);
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
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
    Authentication authentication, HttpSession session){

         String userName=UserDetailHelper.getEmailOfLoggedInUser(authentication);

         // Add Validation 

         if(result.hasErrors()){
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
             
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.RED)
                    .build());

            return "user/add_contact";
         }
           
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

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }

      //  contactService.save(contact);


         session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.GREEN)
                        .build());

        return "redirect:/user/contacts/add";

    }
    
}
