package com.exam.mix.dto.user;


import com.exam.mix.entity.user.Role;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private Long Uid;
    private String createKey;
}
