package com.companyName.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginUserDto extends AdapterUserDto{
	
	@NotEmpty(message = "Enter email adress.")
	@Email(message = "Email should be valid.")
	private String email;

	@NotEmpty(message = "Enter password.")
	private String password;

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
