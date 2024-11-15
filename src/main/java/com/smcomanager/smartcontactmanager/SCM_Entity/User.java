package com.smcomanager.smartcontactmanager.SCM_Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;


    private boolean enable=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    // Login Providers   

    private Providers provider=Providers.SELF;
    private String providerUserId;


    // Mapping with Contact 

     @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts=new ArrayList<>();


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getAbout() {
        return about;
    }


    public void setAbout(String about) {
        this.about = about;
    }


    public String getProfilePic() {
        return profilePic;
    }


    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public boolean isEnable() {
        return enable;
    }


    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public boolean isEmailVerified() {
        return emailVerified;
    }


    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }


    public boolean isPhoneVerified() {
        return phoneVerified;
    }


    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }


    public Providers getProvider() {
        return provider;
    }


    public void setProvider(Providers provider) {
        this.provider = provider;
    }


    public String getProviderUserId() {
        return providerUserId;
    }


    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }


    public List<Contact> getContacts() {
        return contacts;
    }


    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


    




    

    
}
