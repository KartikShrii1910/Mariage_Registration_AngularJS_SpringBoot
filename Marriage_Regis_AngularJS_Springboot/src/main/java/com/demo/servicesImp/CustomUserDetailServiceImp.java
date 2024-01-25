package com.demo.servicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entities.CustomUserDetails;
import com.demo.entities.PersonDetails;
import com.demo.repositories.PersonDetailsRepository;
import com.demo.services.CustomUserDetailService;


@Service
public class CustomUserDetailServiceImp implements  CustomUserDetailService , UserDetailsService{

	@Autowired
	private PersonDetailsRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		PersonDetails user = this.userRepo.findByEmail(email);
		if (user==null) {
			throw new UsernameNotFoundException("No User");
		}
		
		return new CustomUserDetails(user);
	}
	
}
