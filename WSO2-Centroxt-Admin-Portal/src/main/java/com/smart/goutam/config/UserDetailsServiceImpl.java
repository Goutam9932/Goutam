package com.smart.goutam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.goutam.entity.User;
import com.smart.goutam.repo.UserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	
	private UserRepository userRepository;  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//featching user from database
		User user=userRepository.getUserByName(username);
		if(user==null) {
			throw new UsernameNotFoundException("could not found");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
