package music.musicsite.service.refreshtoken;

import music.musicsite.dto.token.TokenDTO;
import music.musicsite.entity.token.Refreshtoken;
import music.musicsite.entity.user.Role;
import music.musicsite.repository.refreshtoken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository repository;

    @Override
    public void saveRefreshToken(String token, String hakbun, Role role) {
        Refreshtoken refreshToken = Refreshtoken.builder()
                .refreshToken(token)
                .hakbun(hakbun)
                .role(role)
                .build();
        repository.save(refreshToken);
    }

    @Override
    public TokenDTO findUserInfomation(String refreshToken) {
        Refreshtoken byrefreshtoken = repository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NullPointerException("리프래쉬 토큰이 존재하지 않습니다."));
        return TokenDTO.builder()
                .hakbun(byrefreshtoken.getHakbun())
                .role(byrefreshtoken.getRole())
                .build();
    }

    @Override
    public void deleteToken(String refreshToken) {
        System.out.println("refreshToken = " + refreshToken);
        Optional<Refreshtoken> refreshtoken = repository.deleteByRefreshToken(refreshToken);
    }

}
