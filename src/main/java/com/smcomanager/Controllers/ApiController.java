package com.smcomanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.Services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    // get contact All this is use for MODEL click on image icon and model show the full detail 

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        return contactService.getById(contactId);
    }

}
