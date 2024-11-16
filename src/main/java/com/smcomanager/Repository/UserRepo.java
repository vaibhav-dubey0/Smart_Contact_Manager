package com.smcomanager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smcomanager.SCM_Entity.Users;


@Repository
public interface UserRepo extends JpaRepository<Users,String>{

    Optional<Users> findByEmail(String email);
    
}
