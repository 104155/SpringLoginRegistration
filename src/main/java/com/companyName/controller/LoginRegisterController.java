package com.companyName.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.companyName.model.EmailConfirmationToken;
import com.companyName.model.User;
import com.companyName.repository.EmailConfirmationTokenRepository;
import com.companyName.repository.UserRepository;
import com.companyName.service.DefaultEmailSenderService;
import com.companyName.service.UserService;

@Controller
public class LoginRegisterController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
    private EmailConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private DefaultEmailSenderService emailSenderService;

	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}

	/*
	 * 'user' object in parameter list creates a new User object which is used to
	 * bind data from the register form
	 */
	@GetMapping("/register")
	public String register(User user) {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid User user, BindingResult bindingResult, HttpServletRequest request, Model model) {
		//validate input
		if (bindingResult.hasErrors()) {
			return "register";
		//user already exists
		} else if (userService.isUserAlreadyPresentFindByEmail(user)) {
			model.addAttribute("message", "User already exists.");
			return "register";
		//save user, save token, send email
		} else {
			userService.createUser(user);
			
            EmailConfirmationToken confirmationToken = new EmailConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();
            
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("[RunTrack] Complete Registration!");
            mailMessage.setFrom("nitram.reyamrebo@gmx.net");
            mailMessage.setText("To confirm your e-mail address, please click/ select and right click/ or copy the link into your browser:\n\n"
					+ appUrl + "/confirm?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmailOLD(mailMessage);
            
            model.addAttribute("emailAddress", user.getEmail());
            return "verifyEmailAddress";
		}
	}
	
	@RequestMapping(value = "/confirm", method = { RequestMethod.GET, RequestMethod.POST })
	public String confirmEmailAddress(Model model, @RequestParam("token") String confirmationToken) {
		
		//findByConfirmationToken() returns ConfirmationToken
		EmailConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if (token != null) {
			//User user = userService.findByEmailIgnoreCase(token.getUser()); //maybe findByUserId not by email
			User user = userRepository.getOne(token.getUser().getUserId());
			user.setStatus("VERIFIED");			
			userRepository.save(user);
			return "accountVerified";
		} else {
			model.addAttribute("message", "The link is invalid or broken.");
			return "error";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/myLogout")
	public String logout() {
		return "logout";
	}
}
