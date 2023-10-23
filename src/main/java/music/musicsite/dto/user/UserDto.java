package music.musicsite.dto.user;

import lombok.*;
import music.musicsite.entity.user.ROLE;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long User_id;
    private int hakbun;
    private String nickname;
    private String password;
    private String confirmKey;
    private ROLE role;
}
