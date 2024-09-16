package college.pb.managementunitapi.config.security.converter;

import college.pb.managementunitapi.config.security.authentication.JWTAuthentication;
import college.pb.managementunitapi.config.security.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

    private final JWTUtil jwtUtil;

    //TODO: This JWTAuthentication always born already authenticated, duplicating the logic of the JWTAuthenticationManager, re-think and re-design this.
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            .map(authHeader -> authHeader.substring(7))
            .map(this::buildJWTAuthentication)
            .doOnError(ex -> log.error("Error while converting JWT", ex));
    }

    private Authentication buildJWTAuthentication(String token) {
        var claims = jwtUtil.getClaimsIfValid(token);
        return new JWTAuthentication(claims.getIssuer(), claims.getSubject(), token,
            claims.get("userId", Long.class), claims.get("userUuid", String.class), jwtUtil.getRolesFromClaims(claims));
    }
}
