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
    public void saveRefreshToken(String token, String email, String role) {
        Refreshtoken refreshToken = Refreshtoken.builder()
                .refreshToken(token)
                .email(email)
                .role(Role.valueOf(role))
                .build();
        repository.save(refreshToken);
    }

    @Override
    public TokenDTO findUserInfomation(String refreshToken) {
        Optional<Refreshtoken> byRefreshToken = repository.findByRefreshToken(refreshToken);
        System.out.println("findByRefreshToken = " + byRefreshToken);
        return TokenDTO.builder()
                .email(byRefreshToken.get().getEmail())
                .role(byRefreshToken.get().getRole())
                .build();
    }

    @Override
    public void deleteToken(String refreshToken) {
        System.out.println("refreshToken = " + refreshToken);
        Optional<Refreshtoken> refreshtoken = repository.deleteByRefreshToken(refreshToken);
    }

}
