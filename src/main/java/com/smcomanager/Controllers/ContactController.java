package com.smcomanager.Controllers;

import org.springframework.validation.BindingResult;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smcomanager.Form_Handler.ContactForm;
import com.smcomanager.Form_Handler.ContactSearchForm;
import com.smcomanager.Helper.AppConstent;
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
    public String addContactView(Model model,Authentication authentication){

        String userName = UserDetailHelper.getEmailOfLoggedInUser(authentication);
        Users loggedInUser = userService.getUserByEmail(userName);
    
        // Add logged-in user to the model
        model.addAttribute("loggedInUser", loggedInUser);

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
         Users users=userService.getUserByEmail(userName);
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

        contactService.save(contact);


         session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.GREEN)
                        .build());

        return "redirect:/user/contacts/add";

    }
    
    @RequestMapping
    public String viewContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = AppConstent.PAGE_SIZE + "") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all the user contacts
        String username = UserDetailHelper.getEmailOfLoggedInUser(authentication);
        Users loggedInUser = userService.getUserByEmail(username);
    
        // Add logged-in user to the model
        model.addAttribute("loggedInUser", loggedInUser);

        Users user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstent.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(defaultValue = AppConstent.PAGE_SIZE + "") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

                String username = UserDetailHelper.getEmailOfLoggedInUser(authentication);
                Users loggedInUser = userService.getUserByEmail(username);
            
                // Add logged-in user to the model
                model.addAttribute("loggedInUser", loggedInUser);

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(UserDetailHelper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstent.PAGE_SIZE);

        return "user/search";
    }



     @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable String contactId,
            HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.GREEN)
                        .build()

        );

        return "redirect:/user/contacts";
    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable String contactId,
            Model model,Authentication authentication) {

                String userName = UserDetailHelper.getEmailOfLoggedInUser(authentication);
        Users loggedInUser = userService.getUserByEmail(userName);
    
        // Add logged-in user to the model
        model.addAttribute("loggedInUser", loggedInUser);



        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Model model) {


        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.GREEN).build());

        return "redirect:/user/contacts";
    }



    
}
