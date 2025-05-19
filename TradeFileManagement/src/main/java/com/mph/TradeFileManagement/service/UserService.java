package com.mph.TradeFileManagement.service;

import java.util.List;import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mph.TradeFileManagement.dao.UserRepository;
import com.mph.TradeFileManagement.model.User;

@Service
public class UserService implements UserDetailsService{
    @Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        return new org.springframework.security.core.userdetails.User(
	            user.getUsername(),
	            user.getPassword(),
	            List.of(new SimpleGrantedAuthority(user.getRole()))
	         );
		
	}
	
	public Optional<User> getRole(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	
	public List<User> getAllUsers(){
		return  userRepository.findAll();
	}
	
	
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
	
	public User addUserByAdmin(User request) {
		return userRepository.save(request);
	}
  
}
