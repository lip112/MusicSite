package com.exam.mix.repository.refreshtoken;

import com.exam.mix.entity.token.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Refreshtoken, Long> {
    Optional<Refreshtoken> findByEmail(String email);

    Optional<Refreshtoken> findByRefreshToken(String refreshToken);

    Optional<Refreshtoken> deleteByRefreshToken(String refreshToken);

}
