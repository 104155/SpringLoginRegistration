package com.companyName.dto;

import java.util.Set;

import com.companyName.model.Role;

public interface UserDto {

	public abstract String getFirstName();

	public abstract String getLastName();

	public abstract String getEmail();

	public abstract String getPassword();

	public abstract String getNewPassword();

	public abstract String getStatus();

	public abstract Set<Role> getRoles();
}
