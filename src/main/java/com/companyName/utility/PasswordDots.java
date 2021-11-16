package com.companyName.utility;

import org.springframework.stereotype.Component;

@Component
public class PasswordDots {

	public String getPasswordDots(int dotNumber) {
		String dots = "";
		for (int i = 0; i < dotNumber; i++) {
			dots += "*";
		}
		return dots;
	}
}
