package com.smcomanager.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smcomanager.SCM_Entity.Contact;
import com.smcomanager.SCM_Entity.Users;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    // find the contact by user
    // custom finder method
    Page<Contact> findByUser(Users user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(Users user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(Users user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(Users user, String phonekeyword, Pageable pageable);

   

}
