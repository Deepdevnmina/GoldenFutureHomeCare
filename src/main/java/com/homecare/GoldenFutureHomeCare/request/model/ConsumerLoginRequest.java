package com.homecare.GoldenFutureHomeCare.request.model;

public class ConsumerLoginRequest {
	private String email;
	private String password;

	public ConsumerLoginRequest() {

	}

	public ConsumerLoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
