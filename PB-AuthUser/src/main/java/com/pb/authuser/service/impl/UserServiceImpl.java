package com.pb.authuser.service.impl;

import com.pb.authuser.dto.UserDto;
import com.pb.authuser.enums.UserRole;
import com.pb.authuser.enums.UserStatus;
import com.pb.authuser.models.entity.UserModel;
import com.pb.authuser.models.exceptions.EmailAlreadyExistsException;
import com.pb.authuser.models.exceptions.UserNotFoundException;
import com.pb.authuser.models.exceptions.UsernameAlreadyExistsException;
import com.pb.authuser.repository.UserRepository;
import com.pb.authuser.service.UserService;
import com.pb.authuser.utils.CustomBeanUtils;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<List<UserModel>> findAllUsers() {
        return userRepository.findAll().collectList();
    }

    @Override
    public Mono<UserModel> findUserById(Long userId) {
        return userRepository.findById(userId).filter(Objects::isNull)
            .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

    @Override
    public Mono<UserModel> registerUser(UserDto userDto) {
        return this.existsByUsername(userDto.getUsername())
            .then(this.existsByEmail(userDto.getEmail())).thenReturn(userDto)
            .doOnNext(user -> userDto.setPassword(passwordEncoder.encode(userDto.getPassword())))
            .then(Mono.defer(() -> {
                var user = convertUserDtoToUser(userDto);
                return userRepository.save(user); // assuming save() returns Mono<User>
            }));
    }

    @Override
    public Mono<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
            .cast(UserModel.class)
            .switchIfEmpty(Mono.error(UserNotFoundException::new))
            .map(this::convertUserToUserDto);
    }

    @Override
    public Mono<UserDto> updateUser(UserDto userDto) {
        return userRepository.findById(userDto.getId())
            .doOnNext(user -> {
                CustomBeanUtils.copyNonNullProperties(userDto, user);
            })
            .flatMap(userRepository::save)
            .map(this::convertUserToUserDto);
    }

    private Mono<Void> existsByUsername(String username) {
        return userRepository.existsByUsername(username)
            .filter(bol -> !bol)
            .switchIfEmpty(Mono.error(UsernameAlreadyExistsException::new))
            .then();
    }

    private Mono<Void> existsByEmail(String email) {
        return userRepository.existsByEmail(email)
            .filter(bol -> !bol)
            .switchIfEmpty(Mono.error(EmailAlreadyExistsException::new))
            .then();
    }

    private UserModel convertUserDtoToUser(UserDto userDto) {
        var user = new UserModel();
        BeanUtils.copyProperties(userDto, user);
        applyUserRoleAndStatus(user);
        user.setUuid(UUID.randomUUID().toString());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserRole(UserRole.ROLE_USER);
        return user;
    }

    private void applyUserRoleAndStatus(UserModel user) {
        user.setUserRole(UserRole.ROLE_USER);
        user.setUserStatus(UserStatus.ACTIVE);
    }

    private UserDto convertUserToUserDto(UserModel user) {
        var userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }


}
