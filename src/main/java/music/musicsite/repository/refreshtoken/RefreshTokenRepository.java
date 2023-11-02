package music.musicsite.repository.refreshtoken;

import music.musicsite.entity.token.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Refreshtoken, Long> {
    Optional<Refreshtoken> findByHakbun(String hakbun);

    Optional<Refreshtoken> findByRefreshToken(String refreshToken);

    Optional<Refreshtoken> deleteByRefreshToken(String refreshToken);

}
