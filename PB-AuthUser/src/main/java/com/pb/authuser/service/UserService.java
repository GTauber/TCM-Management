package com.pb.authuser.service;

import com.pb.authuser.dto.UserDto;
import com.pb.authuser.models.entity.UserModel;
import java.util.List;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<List<UserModel>> findAllUsers();

    Mono<UserModel> findUserById(Long userId);

    Mono<UserModel> registerUser(UserDto userDto);

    Mono<UserDto> findByUsername(String username);

    Mono<UserDto> updateUser(UserDto userDto);
}
