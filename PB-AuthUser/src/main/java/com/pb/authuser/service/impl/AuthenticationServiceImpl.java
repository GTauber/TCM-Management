package com.pb.authuser.service.impl;

import com.pb.authuser.config.security.utils.JWTUtil;
import com.pb.authuser.models.entity.TokenResponse;
import com.pb.authuser.models.entity.UserModel;
import com.pb.authuser.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTUtil jwtUtil;

    @Override
    public TokenResponse generateTokenResponse(Authentication authentication) {
        var user = (UserModel) authentication.getPrincipal();
        log.info("Generating token response for user id: [{}]", user.getId());
        return new TokenResponse(
            "Bearer " + jwtUtil.generateToken(authentication),
            user.getUsername(),
            user.getFullName(),
            user.getId()
        );
    }

}
