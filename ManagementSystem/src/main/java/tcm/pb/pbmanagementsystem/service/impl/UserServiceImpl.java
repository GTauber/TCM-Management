package tcm.pb.pbmanagementsystem.service.impl;


import tcm.pb.pbmanagementsystem.model.dto.UserDto;
import tcm.pb.pbmanagementsystem.model.entity.User;
import tcm.pb.pbmanagementsystem.repository.UserRepository;
import tcm.pb.pbmanagementsystem.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    @Override
    public User createUser(UserDto user) {
        log.info("Creating user: {}", user);
        return userRepository.save(Objects.requireNonNull(conversionService.convert(user, User.class)));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User updateUser(UserDto userDto) {
        var user = userRepository.findById(userDto.id())
            .orElseThrow();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

