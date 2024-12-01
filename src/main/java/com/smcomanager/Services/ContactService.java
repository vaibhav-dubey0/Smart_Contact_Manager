package com.smcomanager.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.SCM_Entity.Users;

public interface ContactService {



     Contact save(Contact contact);

    // update contact
    Contact update(Contact contact);

    // get contacts
    List<Contact> getAll();

    // get contact by id

    Contact getById(String id);

    // delete contact

    void delete(String id);

    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, Users user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, Users user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            Users user);

    // get contacts by userId
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(Users user, int page, int size, String sortField, String sortDirection);

    
}
