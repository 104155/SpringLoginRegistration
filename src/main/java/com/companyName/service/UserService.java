package com.companyName.service;

import com.companyName.dto.UserDto;
import com.companyName.model.User;

public interface UserService {
	
	public void createUser(User user);
	
	public UserInSession updateUser(UserDto userDtoTest, UserInSession userInSession);
		
	public boolean isUserAlreadyPresentFindById(User user);
	
	public boolean isUserAlreadyPresentFindByEmail(User user);
	
	public User findByEmailIgnoreCase(User user);
	
	public User findByEmailIgnoreCase(String email);
	
	public boolean isEmailAlreadyTakenFindByEmail(String email);
	
	public boolean passwordMatching(String rawPassword, UserInSession userInSession);
}