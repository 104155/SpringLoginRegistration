package com.companyName.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDto extends AdapterUserDto {

	@NotEmpty(message = "Enter email.")
	@Email(message = "Invalid email address.")
	private String email;

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
