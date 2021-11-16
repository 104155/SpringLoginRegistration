package com.companyName.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/homeUser")
	public String homeUser() {
		return "homeUser";
	}

	@GetMapping("/homeAdmin")
	public String homeAdmin() {
		return "homeAdmin";
	}
}
