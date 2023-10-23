package com.exam.mix.service.user;

import com.exam.mix.dto.user.UserDTO;
import com.exam.mix.entity.user.User;


public interface UserService {
    UserDTO signUp(UserDTO userDTO);
    UserDTO login(UserDTO userDTO) ;

    UserDTO  logout(UserDTO userDTO);
    void updatePassword(UserDTO userDTO);
    boolean  checkDuplicateNickname(String nickname);

    default User DtoToEntity(UserDTO userDTO){
        User user = User.builder()
                .email(userDTO.getEmail())
                .nickname(userDTO.getNickname())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
        return user;
    }

    default UserDTO EntityToDto(User user) {
        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .role(user.getRole())
                .Uid(user.getUid())
                .build();
        return userDTO;
    }
}
