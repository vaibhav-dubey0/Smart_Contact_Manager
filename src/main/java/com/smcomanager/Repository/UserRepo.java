package com.smcomanager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smcomanager.smartcontactmanager.SCM_Entity.User;



public interface UserRepo extends JpaRepository<User,String>{

    Optional<User> findByEmail(String email);
    
}
