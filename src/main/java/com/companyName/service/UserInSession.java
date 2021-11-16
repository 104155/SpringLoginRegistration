package com.companyName.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.companyName.model.User;

@Component
public class UserInSession {

	private static int queryCounter;
	
	@Autowired
	private User user;

	@Autowired
	private UserService userService;
	
	public User getUserInSession() {
        if (userNotComplete()) {
			queryCounter++;
            user = userService.findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        System.out.println("UserInSession QueryCounter: " + queryCounter);
		return user;
	}

	public boolean userNotComplete() {
		boolean firstName = user.getFirstName() == null; 
		boolean lastName = user.getLastName() == null; 
		boolean email = user.getEmail() == null; 
		boolean password = user.getPassword() == null; 
		if(firstName || lastName || email || password) {
		 return true;	
		} else {
		return false;
		}
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
