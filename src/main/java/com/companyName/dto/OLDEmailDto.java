package com.companyName.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.companyName.validation.ValidationChangeEmail;

public class OLDEmailDto {
	
	@NotEmpty(message = "Enter email.")
	@Email(message = "Invalid email address.")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
