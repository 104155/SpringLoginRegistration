package com.companyName.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.companyName.dto.EmailDto;
import com.companyName.dto.OLDEmailDto;
import com.companyName.model.SecureToken;
import com.companyName.service.DefaultEmailSenderService;
import com.companyName.service.DefaultSecureTokenService;
import com.companyName.service.UserInSession;
import com.companyName.service.UserService;

@Controller
public class ResetPasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private DefaultEmailSenderService emailSenderService;

	@Autowired
	private DefaultSecureTokenService secureToken;

	@GetMapping("/resetPassword")
	public String showResetPasswordPage(EmailDto emailDto) {
		return "resetPassword";
	}

	@PostMapping("/resetPassword")
	public String validateEmailAdress(@Valid EmailDto emailDto, BindingResult bindingResult, Model model) {
		// check if email adress is well-formed & not empty
		if (bindingResult.hasErrors()) {
			return "resetPassword";
		}
		// check if email address is not in DB
		else if (!userService.isEmailAlreadyTakenFindByEmail(emailDto.getEmail())) {
			bindingResult.addError(new FieldError("emailDto", "email", "Invalid email address."));
			return "resetPassword";
		}
		// create token and send email
		else {
			emailSenderService.sendEmail(userService.findByEmailIgnoreCase(emailDto.getEmail()), "Reset your password!",
					"To reset your password", secureToken);
			model.addAttribute("emailAddress", emailDto.getEmail());
			return "verifyEmailAddress";
		}
	}

//	@PostMapping("/newPassword")
//	public String verifyToken(String token) {
//		SecureToken dbSecureToken = secureToken.findByToken(token);
//		//"invalidToken" "expired"
//		
//		if(dbSecureToken != null) {
//			if(//is expired?) {
//				return"";
//			} else { //token expired
//			return "";
//			}
//		} else {
//			// Invalid Token
//			return "";
//		}
//	}
//
//	@PostMapping("/newPassword")
//	public String enterNewPasswordPage(@Valid NewPasswordDto newPasswordDto , BindingResult bindingResult, Model model) {
//		model.addAttribute("message", "You have a new password.");
//		return "login";
//	}
}
