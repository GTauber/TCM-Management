package com.pb.authuser.config.security.matchers.entries;

import com.pb.authuser.config.security.managers.JWTAuthenticationManager;
import com.pb.authuser.config.security.matchers.JWTValidationMatcher;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthMatcherEntry extends ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager> {


    public JWTAuthMatcherEntry(JWTValidationMatcher jwtValidationMatcher, JWTAuthenticationManager jwtAuthenticationManager) {
        super(jwtValidationMatcher, jwtAuthenticationManager);
    }
}
