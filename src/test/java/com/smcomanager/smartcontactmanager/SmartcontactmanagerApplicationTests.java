package com.smcomanager.smartcontactmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smcomanager.Services.EmailService;

@SpringBootTest
class SmartcontactmanagerApplicationTests {



	@Test
	void contextLoads() {
	}

	

    @Autowired
    private EmailService eService;  // Autowire the service correctly

    @Test
    void sendEmailTest() {
        // Ensure that eService is not null before calling sendEmail
        if (eService != null) {
            eService.sendEmail("dubey.vaibhav.web@gmail.com", "For Testing Purpose", "Send or not");
        } else {
            System.out.println("EmailService is not available");
        }
    }
}
