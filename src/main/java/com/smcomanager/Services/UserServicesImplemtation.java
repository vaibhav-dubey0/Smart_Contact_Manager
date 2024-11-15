package com.smcomanager.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smcomanager.Helper.ResorceNotFoundException;
import com.smcomanager.Repository.UserRepo;
import com.smcomanager.smartcontactmanager.SCM_Entity.User;


@Service
public class UserServicesImplemtation implements UserServices{
    
    @Autowired
    UserRepo userRepo;

    Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {

      String uid=UUID.randomUUID().toString();
      user.setId(uid);
      return userRepo.save(user);
      
    }

    @Override
    public List<User> getAllUsers() {
      
        return userRepo.findAll();

    }

    @Override
    public Optional<User> getUserById(String id) {
     
         return userRepo.findById(id);
        
    }

    @Override
    public Optional<User> updateUser(User user) {
     User user2=  userRepo.findById(user.getId()).orElseThrow(()-> new ResorceNotFoundException("User Not Found"));
      
     user2.setName(user.getName());
     user2.setEmail(user.getEmail());
     user2.setAbout(user.getAbout());
     user2.setContacts(user.getContacts());
     user2.setPassword(user.getPassword());
     user2.setPhoneNumber(user.getPhoneNumber());
     user2.setProfilePic(user.getProfilePic());
     user2.setEmailVerified(user.isEmailVerified());
     user2.setEnable(user.isEnable());
     user2.setPhoneVerified(user.isPhoneVerified());
     user2.setProviderUserId(user.getProviderUserId());
     user2.setProvider(user.getProvider());


     User save=userRepo.save(user2);
     return Optional.ofNullable(save);

     
    }

    @Override
    public void deleteUser(String id) {

     User user1=userRepo.findById(id).orElseThrow( () -> new ResorceNotFoundException(" User Not Found "));
     userRepo.delete(user1);

        
    }

    @Override
    public boolean isUserExist(String id) {

      User user1=userRepo.findById(id).orElse(null);
      return user1!=null ? true: false;
       
    }

    @Override
    public boolean isUserExistByEmail(String email) {

      User user1=userRepo.findByEmail(email).orElse(null);
      return user1!=null ? true: false;
       
    }

    
    
}
