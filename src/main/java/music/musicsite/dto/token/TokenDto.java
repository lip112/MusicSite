package music.musicsite.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import music.musicsite.entity.user.ROLE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String hakbun;
    private ROLE role;
    private String key;

}
