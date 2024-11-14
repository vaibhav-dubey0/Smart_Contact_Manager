package com.smcomanager.Services;

import java.util.List;
import java.util.Optional;

import com.smcomanager.smartcontactmanager.SCM_Entity.User;

public interface UserServices {
 
    User savUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistByEmail(String email);   

}
