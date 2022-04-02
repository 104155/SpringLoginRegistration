package com.companyName.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.companyName.dto.DefaultUserDto;
import com.companyName.model.EmailConfirmationToken;
import com.companyName.model.User;
import com.companyName.repository.EmailConfirmationTokenRepository;
import com.companyName.repository.UserRepository;
import com.companyName.service.DefaultEmailSenderService;
import com.companyName.service.UserInSession;
import com.companyName.service.UserService;
import com.companyName.utility.PasswordDots;
import com.companyName.validation.ValidationChangeEmail;
import com.companyName.validation.ValidationChangeName;
import com.companyName.validation.ValidationChangePassword;

@Controller
public class UserProfileController {

	@Autowired
	private UserInSession userInSession;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private DefaultEmailSenderService emailSenderService;
	
	@Autowired
	private PasswordDots passwordConverter;

	@GetMapping("/userProfile")
	public String userProfile(DefaultUserDto userDto, Model model) {
		User user = userInSession.getUserInSession();
		// display user information
		String fullUserName = user.getFirstName() + " " + user.getLastName();
		model.addAttribute("fullUserName", fullUserName);
		String email = user.getEmail();
		model.addAttribute("emailAddress", email);
		String passwordDots = passwordConverter.getPasswordDots(user.getInitialPasswordLength());
		model.addAttribute("passwordDots", passwordDots);
		// form remove error messages
		userDto.setFirstName(null);
		userDto.setLastName(null);
		userDto.setEmail(null);
		userDto.setPassword(null);
		userDto.setNewPassword(null);
		return "userProfile";
	}

	@PostMapping("/changeName")
	public String changeName(@Validated(ValidationChangeName.class) DefaultUserDto userDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// open popup
			model.addAttribute("popupActiveName", true);
			return "userProfile";
		} else {
			// update this.userInSession and update user in DB
			this.userInSession = userService.updateUser(userDto, userInSession);
			model.addAttribute("message", "Name changed.");
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// clear fields in name-popup
			userDto.setFirstName(null);
			userDto.setLastName(null);
			return "userProfile";
		}
	}

	@PostMapping("/changeEmail")
	public String changeEmail(@Validated(ValidationChangeEmail.class) DefaultUserDto userDto,
			BindingResult bindingResult, HttpServletRequest request, Model model) {
		// checking password is not null && is not empty "" && is invalid
		if (userDto.getPassword() != null && !(userDto.getPassword().equals(""))
				&& !(userService.passwordMatching(userDto.getPassword(), userInSession))) {
			// add pw invalid error
			bindingResult.addError(new FieldError("UserDtoTest", "password", "invalid password"));
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// open popup
			model.addAttribute("popupActiveEmail", true);
			return "userProfile";
		}
		// checking password null and other field errors
		else if (bindingResult.hasErrors()) {
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);			
			// open popup
			model.addAttribute("popupActiveEmail", true);
			return "userProfile";
		}
		// changing email address
		else {
			//change user status to pending
			userInSession.getUserInSession().setStatus("PENDING");
			
			// update this.userInSession and update user in DB
			this.userInSession = userService.updateUser(userDto, userInSession);
			String currentEmailAddress = userInSession.getUserInSession().getEmail();

			// send email to new email address, create CT for updated UserInSession
			EmailConfirmationToken confirmationToken = new EmailConfirmationToken(userInSession.getUserInSession());
			confirmationTokenRepository.save(confirmationToken);

			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(userInSession.getUserInSession().getEmail());
			mailMessage.setSubject("[RunTrack] New Email-address!");
			mailMessage.setFrom("nitram.reyamrebo@gmx.net");
			mailMessage
					.setText("To confirm your e-mail address, please click/ select and right click/ or copy the link into your browser:\n"
							+ appUrl + "/confirm?token=" + confirmationToken.getConfirmationToken());

			emailSenderService.sendEmailOLD(mailMessage);

			model.addAttribute("message", "Email address changed.");
			model.addAttribute("emailAddress", currentEmailAddress);
			return "verifyEmailAddress";
		}
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@Validated(ValidationChangePassword.class) DefaultUserDto userDto,
			BindingResult bindingResult, HttpServletRequest request, Model model) {
		// checking password is not null && is not empty "" && is invalid
		if (userDto.getPassword() != null && !(userDto.getPassword().equals(""))
				&& !(userService.passwordMatching(userDto.getPassword(), userInSession))) {
			// add pw invalid error
			bindingResult.addError(new FieldError("UserDto", "password", "Invalid password."));
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// open popup
			model.addAttribute("popupActivePassword", true);
			return "userProfile";
		}
		// checking password null and other field errors
		else if (bindingResult.hasErrors()) {
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// open popup
			model.addAttribute("popupActivePassword", true);
			return "userProfile";
		}
		// changing password
		else {			
			// update this.userInSession and update user in DB
			this.userInSession = userService.updateUser(userDto, userInSession);
			// display user information
			String fullUserName = userInSession.getUserInSession().getFirstName() + " "
					+ userInSession.getUserInSession().getLastName();
			model.addAttribute("fullUserName", fullUserName);
			String email = userInSession.getUserInSession().getEmail();
			model.addAttribute("emailAddress", email);
			String passwordDots = passwordConverter.getPasswordDots(userInSession.getUserInSession().getInitialPasswordLength());
			model.addAttribute("passwordDots", passwordDots);
			// clear fields in password-popup
			userDto.setPassword(null);
			userDto.setNewPassword(null);
			// add message
			model.addAttribute("message", "Password changed.");
			return "userProfile";
		}
	}
}
