package com.pb.authuser.config.security.authentication;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * JWTAuthentication is a custom authentication token that extends the AbstractAuthenticationToken class.
 * It represents the authentication for a JWT token.
 * <p>
 * This class holds the principal and credentials for an authentication request, as well as the JWT token itself.
 * The principal and credentials are usually set by an instance of AuthenticationManager.
 * The JWT token is set when the class is instantiated.
 * <p>
 * The class also overrides the getCredentials and getPrincipal methods from the AbstractAuthenticationToken class.
 *
 * @author GTauber
 * @version 1.0
 * @since 2024.03.13
 * @see org.springframework.security.authentication.AbstractAuthenticationToken
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class JWTAuthentication extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String issuer;

    private final String principal;

    private String credentials;

    private String token;

    private Long userId;

    private String userUuid;

    public JWTAuthentication(String issuer, String principal, String token, Long userId, String userUuid,
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.issuer = issuer;
        this.principal = principal;
        this.token = token;
        this.userId = userId;
        this.userUuid = userUuid;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
