package com.pb.authuser.config.security.utils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.pb.authuser.models.entity.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class JWTUtil {

    private static final String JWT_KEY = "R2FicmllbCBUYXViZXIgw6kgbyBob21lbSBtYWlzIGxpbmRvIGRvIHVuaXZlcnNv";

    public static final String JWT_HEADER_PREFIX = "Bearer ";

    private static final Long LONG_TIME = 3600000000000000000L;

    public String generateToken(Authentication authentication) {
        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        var user = (UserModel) authentication.getPrincipal();
        return Jwts.builder()
            .issuer("easyAuth")
            .subject(authentication.getName())
            .claim("userId", user.getId())
            .claim("userUuid", user.getUuid())
            .claim("roles", populateRoles(authentication.getAuthorities()))
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + LONG_TIME))
            .signWith(key)
            .compact();
    }


    public static boolean isJWTRequest(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().containsKey(AUTHORIZATION) &&
            Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst(AUTHORIZATION))
                .startsWith(JWT_HEADER_PREFIX);
    }

    public Claims getIfValid(String token) {
        return isTokenValid(token) ?
            getAllClaimsFromToken(token) : null; //TODO Throws error if not valid!
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        return Optional.ofNullable(getClaimFromToken(token, claims -> claims.get("roles", String.class)))
            .map(roles -> Stream.of(roles.split(","))
                .map(role -> (GrantedAuthority) () -> role)
                .toList())
            .orElse(null);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        return (Boolean.FALSE.equals(isTokenExpired(token)));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String populateRoles(Collection<? extends GrantedAuthority> roles) {
        return roles.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    }

    private Claims getAllClaimsFromToken(String token) {
        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

}
