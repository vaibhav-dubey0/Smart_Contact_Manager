package com.smcomanager.Services;

import java.util.List;
import java.util.Optional;

import com.smcomanager.SCM_Entity.Users;

public interface UserService {

    Users saveUser(Users user);

    Optional<Users> getUserById(String id);

    Optional<Users> updateUser(Users user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<Users> getAllUsers();

    Users getUserByEmail(String email);
}
