package com.companyName.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.companyName.model.EmailConfirmationToken;

@Repository
public interface EmailConfirmationTokenRepository extends CrudRepository<EmailConfirmationToken, String> {
	EmailConfirmationToken findByConfirmationToken(String confirmationToken);
}
