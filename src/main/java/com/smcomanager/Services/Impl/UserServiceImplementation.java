package com.smcomanager.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smcomanager.Helper.AppConstent;
import com.smcomanager.Helper.ResourceNotFoundException;
import com.smcomanager.Repository.UserRepo;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Users saveUser(Users user) {
        String uid = UUID.randomUUID().toString();
        user.setId(uid);
  
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstent.ROLE_USER));
        
        logger.info(user.getProvider().toString());

        return userRepo.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<Users> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<Users> updateUser(Users user) {
        Users existingUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());
        existingUser.setContacts(user.getContacts());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setProfilePic(user.getProfilePic());
        existingUser.setEmailVerified(user.isEmailVerified());
        existingUser.setEnabled(user.isEnabled());
        existingUser.setPhoneVerified(user.isPhoneVerified());
        existingUser.setProviderUserId(user.getProviderUserId());
        existingUser.setProvider(user.getProvider());

        Users updatedUser = userRepo.save(existingUser);
        return Optional.ofNullable(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user);
    }

    @Override
    public boolean isUserExist(String id) {
        return userRepo.existsById(id);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
    
}

