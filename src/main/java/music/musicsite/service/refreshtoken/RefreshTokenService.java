package music.musicsite.service.refreshtoken;

import music.musicsite.dto.token.TokenDTO;
import music.musicsite.entity.user.Role;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, String hakbun, Role role);

    TokenDTO findUserInfomation(String refreshToken);

    void deleteToken(String refreshToken);

}
