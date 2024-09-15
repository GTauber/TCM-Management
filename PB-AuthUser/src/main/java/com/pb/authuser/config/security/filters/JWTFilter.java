//package com.pb.authuser.config.security.filters;
//
//import com.pb.authuser.config.security.utils.JWTTokenUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.lang.NonNull;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class JWTFilter implements WebFilter {
//
//    private final JWTTokenUtil jwtTokenUtil;
//
//    @Override
//    @NonNull
//    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
//        return ReactiveSecurityContextHolder.getContext()
//            .doOnNext(ctx -> exchange.getResponse().getHeaders().set(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(ctx.getAuthentication())))
//            .then(chain.filter(exchange));
//    }
//}
