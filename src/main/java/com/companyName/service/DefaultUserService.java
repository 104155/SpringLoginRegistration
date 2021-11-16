package com.companyName.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.companyName.dto.DefaultUserDto;
import com.companyName.dto.UserDto;
import com.companyName.model.Role;
import com.companyName.model.User;
import com.companyName.repository.RoleRepository;
import com.companyName.repository.UserRepository;
import com.companyName.utility.PasswordDots;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordDots passwordConverter;

	@Override
	public void createUser(User user) {
		user.setInitialPasswordLength(user.getPassword().length());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setStatus("PENDING");
		Role userRole = roleRepository.findByRole("APP_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public UserInSession updateUser(UserDto userDto, UserInSession userInSession) {
		User user = userInSession.getUserInSession();
		// first name
		if (userDto.getFirstName() != null) {
			user.setFirstName(userDto.getFirstName());
		}
		// last name
		if (userDto.getLastName() != null) {
			user.setLastName(userDto.getLastName());
		}
		// email
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
		}
		// password & initialPasswordLength
		if (userDto.getPassword() != null && userDto.getNewPassword() != null) {
			user.setPassword(encoder.encode(userDto.getNewPassword()));
			user.setInitialPasswordLength(userDto.getNewPassword().length());
		}
		// status
		else if (userDto.getStatus() != null) {
			user.setStatus(userDto.getStatus());
		}
		// roles
		else if (userDto.getRoles() != null) {
			user.setRoles(userDto.getRoles());
		}
		userRepository.save(user);
		// update userInSession
		userInSession.setUser(user);
		return userInSession;
	}

	@Override
	public boolean isUserAlreadyPresentFindById(User user) {
		return userRepository.findById(user.getUserId()).isPresent();
	}

	@Override
	public boolean isUserAlreadyPresentFindByEmail(User user) {
		User repoUser = userRepository.findByEmailIgnoreCase(user.getEmail());
		if (repoUser != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User findByEmailIgnoreCase(User user) {
		return userRepository.findByEmailIgnoreCase(user.getEmail());
	}

	@Override
	public User findByEmailIgnoreCase(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public boolean isEmailAlreadyTakenFindByEmail(String email) {
		User repoUser = userRepository.findByEmailIgnoreCase(email);
		if (repoUser != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean passwordMatching(String rawPassword, UserInSession userInSession) {
		if (encoder.matches(rawPassword, userInSession.getUserInSession().getPassword())) {
			return true;
		} else {
			return false;
		}
	}
}
