package music.musicsite.service.refreshtoken;

import music.musicsite.dto.token.TokenDTO;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, String email, String role);

    TokenDTO findUserInfomation(String refreshToken);

    void deleteToken(String refreshToken);

}
