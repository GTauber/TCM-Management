//package com.pb.authuser.config.security.handler;
//
//import com.pb.authuser.config.security.utils.JWTTokenUtil;
//import com.pb.authuser.models.entity.UserModel;
//import com.pb.authuser.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class SuccessHandler implements ServerAuthenticationSuccessHandler {
//
//    private final UserRepository usersRepository;
//
//    private final JWTTokenUtil jwtTokenUtil;
//
//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
//        response.setStatusCode(HttpStatus.OK);
//        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();
//
//        return usersRepository.findByUsername(username)
//            .cast(UserModel.class)
//            .map(user -> {
//                var token = jwtTokenUtil.generateToken(user);
//                return response.bufferFactory().wrap(token.getBytes());
//                })
//            .flatMap(buffer -> response.writeWith(Mono.just(buffer)));
//
//    }
//}
