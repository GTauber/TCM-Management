package tcm.pb.pbmanagementsystem.implTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tcm.pb.pbmanagementsystem.model.dto.UserDto;
import tcm.pb.pbmanagementsystem.model.entity.User;
import tcm.pb.pbmanagementsystem.repository.UserRepository;
import tcm.pb.pbmanagementsystem.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import tcm.pb.pbmanagementsystem.service.impl.UserServiceImpl;

import java.util.Optional;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto(1L, "John Doe", "john.doe@example.com", "password", UserType.USER);
        User user = new User(1L, 1L, "John Doe", "john.doe@example.com", "password", UserType.USER, null, null);

        when(conversionService.convert(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(userDto.id(), createdUser.getId());
        assertEquals(userDto.name(), createdUser.getName());
        assertEquals(userDto.email(), createdUser.getEmail());
        assertEquals(userDto.password(), createdUser.getPassword());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User(userId, 1L, "John Doe", "john.doe@example.com", "password", UserType.USER, null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void testUpdateUser() {
        UserDto userDto = new UserDto(1L, "John Doe", "john.doe@example.com", "password", UserType.USER);
        User existingUser = new User(1L, 1L, "John Doe", "john.doe@example.com", "oldPassword", UserType.USER, null, null);

        when(userRepository.findById(userDto.id())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userDto);

        assertNotNull(updatedUser);
        assertEquals(userDto.id(), updatedUser.getId());
        assertEquals(userDto.name(), updatedUser.getName());
        assertEquals(userDto.email(), updatedUser.getEmail());
        assertEquals(userDto.password(), updatedUser.getPassword());
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
