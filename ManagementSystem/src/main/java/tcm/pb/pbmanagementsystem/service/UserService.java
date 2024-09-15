package tcm.pb.pbmanagementsystem.service;

import tcm.pb.pbmanagementsystem.model.dto.UserDto;
import tcm.pb.pbmanagementsystem.model.entity.User;

public interface UserService {

    User createUser(UserDto user);
    User getUserById(Long id);
    User updateUser(UserDto user);
    void deleteUserById(Long id);

}
