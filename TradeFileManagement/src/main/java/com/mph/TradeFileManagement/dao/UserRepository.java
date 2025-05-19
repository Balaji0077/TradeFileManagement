package com.mph.TradeFileManagement.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.TradeFileManagement.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
	Optional<User> findByUsername(String username);
}
