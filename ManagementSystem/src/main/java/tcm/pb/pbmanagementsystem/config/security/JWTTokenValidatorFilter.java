package tcm.pb.pbmanagementsystem.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tcm.pb.pbmanagementsystem.util.JWTUtil;

@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    public static final String JWT_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = request.getHeader(JWT_HEADER);
        try {
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("No valid token provided");
                return;
            }

            var token = jwt.replace("Bearer ", "");
            var authentication = buildJWTAuthentication(token);

            if (authentication == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token: " + exception.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication buildJWTAuthentication(String token) {
        var claims = jwtUtil.getClaimsIfValid(token);
        if (claims == null) {
            return null;
        }
        return new JWTAuthentication(claims.getIssuer(), claims.getSubject(), token,
            claims.get("userId", Long.class), claims.get("userUuid", String.class), jwtUtil.getRolesFromClaims(claims));
    }
}
