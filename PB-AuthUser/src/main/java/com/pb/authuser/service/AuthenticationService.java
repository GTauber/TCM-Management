package com.pb.authuser.service;

import com.pb.authuser.models.entity.TokenResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    TokenResponse generateTokenResponse(Authentication authentication);

}
