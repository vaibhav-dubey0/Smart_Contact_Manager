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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
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

    

    
}
