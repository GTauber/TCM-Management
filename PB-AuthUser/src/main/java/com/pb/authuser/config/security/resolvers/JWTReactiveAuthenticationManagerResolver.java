package com.pb.authuser.config.security.resolvers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JWTReactiveAuthenticationManagerResolver implements ReactiveAuthenticationManagerResolver<ServerWebExchange> {

    private final List<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>> authenticationManagers;

    private final UserDetailsRepositoryReactiveAuthenticationManager defaultAuthenticationManager;

    @Autowired
    public JWTReactiveAuthenticationManagerResolver(List<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>> authenticationManagers,
        UserDetailsRepositoryReactiveAuthenticationManager defaultAuthenticationManager) {
        this.authenticationManagers = authenticationManagers;
        this.defaultAuthenticationManager = defaultAuthenticationManager;
    }

    @Override
    public Mono<ReactiveAuthenticationManager> resolve(ServerWebExchange exchange) {
        return Flux.fromIterable(this.authenticationManagers)
            .filterWhen(entry -> isMatch(exchange, entry))
            .next()
            .map(ServerWebExchangeMatcherEntry::getEntry)
            .defaultIfEmpty(this.defaultAuthenticationManager);

    }

    private Mono<Boolean> isMatch(ServerWebExchange exchange, ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager> entry) {

        return Mono.just(entry.getMatcher()).flatMap(matcher -> matcher.matches(exchange)).map(MatchResult::isMatch);
    }
}
