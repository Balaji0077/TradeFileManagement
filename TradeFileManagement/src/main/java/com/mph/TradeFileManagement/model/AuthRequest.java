package com.mph.TradeFileManagement.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class AuthRequest {

	@NotNull(message = "Username should not be empty")
	@NotEmpty
	private String username;
	
	@NotNull(message = "Password should not be empty")
	@NotEmpty
	private String password;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
