package com.pb.authuser.config.security.converter;

import static com.pb.authuser.config.security.utils.JWTUtil.isJWTRequest;

import com.pb.authuser.config.security.authentication.JWTAuthentication;
import com.pb.authuser.config.security.utils.JWTUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ServerHttpBasicOrJWTAuthenticationConverter extends ServerHttpBasicAuthenticationConverter {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return isJWTRequest(exchange) ?
        convertToJWTAuthentication(getJWTToken(exchange))
        : super.convert(exchange);
    }

    private String getJWTToken(ServerWebExchange exchange) {
        return Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("Authorization"))
            .substring(7);
    }

    //TODO: This JWTAuthentication always born already authenticated, duplicating the logic of the JWTAuthenticationManager, re-think and re-design this.
    private Mono<Authentication> convertToJWTAuthentication(String token) {
        var claims = jwtUtil.getIfValid(token);
        return Mono.just(new JWTAuthentication(claims.getIssuer(), claims.getSubject(), token,
            claims.get("userId", Long.class), claims.get("userUuid", String.class), jwtUtil.getRolesFromToken(token)));
    }

}
