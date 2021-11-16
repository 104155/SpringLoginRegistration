package com.companyName.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.companyName.model.User;

@Service()
public class DefaultEmailSenderService {

	@Autowired
	private JavaMailSender javaMailSender;

//	@Autowired
//	private DefaultSecureTokenService secureToken;

//	private String defaultEmailSubject = "Complete Registration!";
//	private String defaultEmailText = "To confirm your e-mail address";

	// OLD change in registration and userProfile controller
	@Async
	public void sendEmailOLD(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	// To confirm your e-mail address
	public void sendEmail(User user, String subject, String text, DefaultSecureTokenService secureToken) {
//		String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();

		final String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("[RunTrack] " + subject);
		mailMessage.setFrom("nitram.reyamrebo@gmx.net");		
		mailMessage.setText(text + ", please click/ select and right click/ or copy the link into your browser:\n" 
				+ appUrl + "/confirm?token=" + secureToken.createSecureToken(user));
			
		javaMailSender.send(mailMessage);
	}
	
//	// To confirm your e-mail address
//	public void sendEmail(User user, String subject, String text) {
////		String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();
//
//		final String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setTo(user.getEmail());
//		mailMessage.setSubject("[RunTrack] " + subject);
//		mailMessage.setFrom("nitram.reyamrebo@gmx.net");		
//		mailMessage.setText(text + ", please click/ select and right click/ or copy the link into your browser:\n" 
//				+ appUrl + "/confirm?token=" + secureToken.createSecureToken(user));
//			
//		javaMailSender.send(mailMessage);
//	}

//	public String getDefaultEmailSubject() {
//		return this.defaultEmailSubject;
//	}
//
//	public String getDefaultEmailText() {
//		return this.defaultEmailText;
//	}
}