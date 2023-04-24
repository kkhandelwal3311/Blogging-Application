package com.codewithkk.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithkk.blog.entities.User;
import com.codewithkk.blog.exception.ResourceNotFoundException;
import com.codewithkk.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user from database by username (email)
		
		User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Username ", " email Id "+username , 0));
		return user;
	}

}
