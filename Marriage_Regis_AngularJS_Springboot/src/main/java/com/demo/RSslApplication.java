package com.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSslApplication implements CommandLineRunner {
//	@Autowired
//	private PersonDetailsRepository userRepo;
//	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(RSslApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		PersonDetails user = new PersonDetails();
		
//		user.setEmail("kartik111@gmail.com");
//		user.setName("Kartik Mandloi");
////		user.setLastName("mandloi");
//		user.setPassword(this.bCryptPasswordEncoder.encode("Kartik@123"));
//		user.setRole("ROLE_ADMIN");
//		this.userRepo.save(user);
	 
	}

}
