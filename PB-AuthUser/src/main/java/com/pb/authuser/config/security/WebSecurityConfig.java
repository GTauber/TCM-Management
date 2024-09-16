package com.pb.authuser.config.security;

import com.pb.authuser.config.security.converter.ServerHttpBasicOrJWTAuthenticationConverter;
import com.pb.authuser.config.security.resolvers.JWTReactiveAuthenticationManagerResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CorsSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTReactiveAuthenticationManagerResolver jwtReactiveAuthenticationManagerResolver;

    private final ServerHttpBasicOrJWTAuthenticationConverter serverHttpBasicOrJWTAuthenticationConverter;
    
    private final UserDetailsRepositoryReactiveAuthenticationManager defaultManager;

    @Bean
    public AuthenticationWebFilter authenticationWebFilter() {
        var authWebFilter = new AuthenticationWebFilter(jwtReactiveAuthenticationManagerResolver);
        authWebFilter.setServerAuthenticationConverter(serverHttpBasicOrJWTAuthenticationConverter);

        return authWebFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(CsrfSpec::disable)
            .cors(CorsSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("auth/register", "/actuator/**", "/eureka/**", "/swagger-ui.html",
                    "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                .anyExchange().authenticated()
            )
            .authenticationManager(defaultManager)
            .formLogin(FormLoginSpec::disable)
            .httpBasic(HttpBasicSpec::disable)
            .addFilterAfter(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }

}
