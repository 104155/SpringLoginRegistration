package com.companyName.service;

import com.companyName.model.SecureToken;
import com.companyName.model.User;

public interface SecureTokenService {
    public abstract SecureToken createSecureToken(User user);
    public abstract SecureToken findByToken(final String token);
    public abstract void removeTokenByToken(final String token);
}
