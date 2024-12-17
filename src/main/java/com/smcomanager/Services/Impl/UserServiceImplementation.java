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
import com.smcomanager.Helper.UserDetailHelper;
import com.smcomanager.Repository.UserRepo;
import com.smcomanager.SCM_Entity.Users;
import com.smcomanager.Services.EmailService;
import com.smcomanager.Services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private  UserDetailHelper helper;

    @Override
    public Users saveUser(Users user) {
        // user id : have to generate
        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        // password encode
        // user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role

        user.setRoleList(List.of(AppConstent.ROLE_USER));

        logger.info(user.getProvider().toString());
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        Users savedUser = userRepo.save(user);
        String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;

    }

    @Override
    public Optional<Users> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<Users> updateUser(Users user) {

        Users user2 = userRepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // update karenge user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // save the user in database
        Users save = userRepo.save(user2);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        Users user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExist(String userId) {
        Users user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        Users user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }

}

