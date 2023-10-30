package music.musicsite.dto.user;


import music.musicsite.entity.user.Role;
import lombok.*;
import music.musicsite.entity.user.User;


@Getter
@Setter
public class UserDTO {
    private int hakbun;
    private String password;
    private String nickname;
    private Role role;
    private String createKey;

    @Builder
    public UserDTO(int hakbun, String password, String nickname, Role role, String createKey) {
        this.hakbun = hakbun;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.createKey = createKey;
    }

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .hakbun(user.getHakbun())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }

}
