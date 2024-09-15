package com.pb.authuser.config.security.matchers;

import static com.pb.authuser.config.security.utils.JWTUtil.isJWTRequest;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JWTValidationMatcher implements ServerWebExchangeMatcher {

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        return isJWTRequest(exchange) ?
            MatchResult.match() : MatchResult.notMatch();
    }
}
