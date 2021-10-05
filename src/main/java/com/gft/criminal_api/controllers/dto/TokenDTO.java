package com.gft.criminal_api.controllers.dto;

public class TokenDTO {

	private String Token;

	public TokenDTO() {

	}
	
	public TokenDTO(String token) {
		super();
		Token = token;
	}

	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	
	
}
