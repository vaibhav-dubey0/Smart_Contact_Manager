package com.smcomanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.password.PasswordEncoder;

import com.smcomanager.Repository.UserRepo;

@SpringBootApplication
public class SmartcontactmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartcontactmanagerApplication.class, args);
	}

	@Autowired
	private UserRepo userRepo;

	// @Autowired
	// private PasswordEncoder passwordEncoder;


	// @Override
	// public void run(String... args) throws Exception {
	// 	User user = new User();
	// 	user.setUserId(UUID.randomUUID().toString());
	// 	user.setName("admin");
	// 	user.setEmail("admin@gmail.com");
	// 	user.setPassword(passwordEncoder.encode("admin"));
	// 	user.setRoleList(List.of(AppConstants.ROLE_USER));
	// 	user.setEmailVerified(true);
	// 	user.setEnabled(true);
	// 	user.setAbout("This is dummy user created initially");
	// 	user.setPhoneVerified(true);

	// 	userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {},() -> {
	// 		userRepo.save(user);
	// 		System.out.println("user created");
	// 	});


	// }

}
