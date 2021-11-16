package com.companyName.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.companyName.model.User;
import com.companyName.service.DefaultEmailSenderService;
import com.companyName.service.UserInSession;
import com.companyName.service.UserService;
import com.companyName.service.DefaultUserService;

@Configuration
public class AppConfig {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public UserInSession getUserInSession() {
//		return new UserInSession();
//	}
	
	@Bean
	public User getUser() {
		return new User();
	}
}
