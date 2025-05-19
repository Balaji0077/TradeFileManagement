package com.mph.TradeFileManagement.model;

public class AuthResponse {
   private String jwt;

public String getJwt() {
	return jwt;
}

 public AuthResponse(String jwt) {
	 this.jwt = jwt;
 }
}
