package com.pb.authuser.config.security.managers;

import com.pb.authuser.config.security.authentication.JWTAuthentication;
import com.pb.authuser.config.security.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
            .filter(JWTAuthentication.class::isInstance)
            .cast(JWTAuthentication.class)
            .filter(auth -> jwtUtil.isTokenValid(auth.getToken()))
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid Token")))
            .thenReturn(authentication);
    }
}
