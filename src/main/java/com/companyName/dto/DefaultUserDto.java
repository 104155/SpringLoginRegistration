package com.companyName.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.companyName.model.Role;
import com.companyName.validation.ValidationChangeEmail;
import com.companyName.validation.ValidationChangeName;
import com.companyName.validation.ValidationChangePassword;

public class DefaultUserDto extends AdapterUserDto{

	@NotEmpty(message = "Enter your first name.", groups = ValidationChangeName.class)
	private String firstName;

	@NotEmpty(message = "Enter your last name.", groups = ValidationChangeName.class)
	private String lastName;
	
	@NotEmpty(message = "Enter new email adress.", groups = ValidationChangeEmail.class)
	@Email(message = "Email should be valid.", groups = ValidationChangeEmail.class)
	private String email;

	@NotEmpty(message = "Enter password.", groups = {ValidationChangeEmail.class, ValidationChangePassword.class})
	private String password;
	
//	@NotEmpty(message = "enter new password", groups = ValidationChangePassword.class)
	@Length(min = 5, message="Password should be at least 5 characters.", groups = ValidationChangePassword.class)
	private String newPassword;

	private String status;
	
	private Set<Role> roles;

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	@Override
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
