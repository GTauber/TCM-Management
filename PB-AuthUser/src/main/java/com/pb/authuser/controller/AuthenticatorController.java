package com.pb.authuser.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.pb.authuser.dto.UserDto;
import com.pb.authuser.models.entity.Response;
import com.pb.authuser.models.entity.TokenResponse;
import com.pb.authuser.models.entity.UserModel;
import com.pb.authuser.service.AuthenticationService;
import com.pb.authuser.service.UserService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticatorController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<Response<UserModel>> registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto)
            .map(user -> Response.<UserModel>builder()
                .status(CREATED)
                .statusCode(CREATED.value())
                .message("User created successfully")
                .data(Map.of("User", user))
                .build());
    }

    @GetMapping("/login")
    public Mono<Response<TokenResponse>> login(Authentication authentication) {
        return Mono.just(Response.<TokenResponse>builder()
            .status(OK)
            .statusCode(OK.value())
            .message("User logged successfully")
            .data(Map.of("TokenResponse", authenticationService.generateTokenResponse(authentication)))
            .build());
    }
}
