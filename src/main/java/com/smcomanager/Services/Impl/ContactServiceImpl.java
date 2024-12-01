package com.smcomanager.Services.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.smcomanager.Helper.ResourceNotFoundException;
import com.smcomanager.Repository.ContactRepo;
import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
       String userId=UUID.randomUUID().toString();
       contact.setId(userId);
       return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
           
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not Found with provided id" + id));
    }

    @Override
    public void delete(String id) {

      contactRepo.deleteById(id);

    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, Users user) {
      
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            Users user) {
       
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            Users user) {
       
    }

    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(Users user, int page, int size, String sortField, String sortDirection) {
        
    }
    
}
