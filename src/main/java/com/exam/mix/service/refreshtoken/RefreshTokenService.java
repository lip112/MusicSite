package com.exam.mix.service.refreshtoken;

import com.exam.mix.dto.token.TokenDTO;
import com.exam.mix.entity.token.Refreshtoken;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, String email, String role);

    TokenDTO findUserInfomation(String refreshToken);

    void deleteToken(String refreshToken);

}
