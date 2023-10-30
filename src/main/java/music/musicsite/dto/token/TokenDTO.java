package music.musicsite.dto.token;

import music.musicsite.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private Role role;
    private String key;
}
