package music.musicsite.dto.user;


import music.musicsite.entity.user.Role;
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
