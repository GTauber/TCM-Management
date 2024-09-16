package tcm.pb.pbmanagementsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTUtil {

    private static final String JWT_KEY = "R2FicmllbCBUYXViZXIgw6kgbyBob21lbSBtYWlzIGxpbmRvIGRvIHVuaXZlcnNv";

    public boolean isTokenValid(String token) {

        return true;
//            (Boolean.FALSE.equals(isTokenExpired(token)));
    }

    public Claims getClaimsIfValid(String token) {
        return isTokenValid(token) ?
            getAllClaimsFromToken(token) : null; //TODO Throws error if not valid!
    }

    public List<GrantedAuthority> getRolesFromClaims(Claims claims) {
        return Arrays.stream(claims.get("roles", String.class).split(","))
            .map(role -> (GrantedAuthority) () -> role)
            .toList();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Claims getAllClaimsFromToken(String token) {
        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

}
