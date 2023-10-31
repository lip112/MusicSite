package music.musicsite.repository.user;

import music.musicsite.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u " +
            " from User u " +
            " where u.nickname = :nickname")
    Optional<User> findByNickname(@Param("nickname") String nickname);

    Optional<User> findByHakbunAndPassword(String hakbun, String password);
    Optional<User> findByHakbun(String hakbun);

//
//    @Modifying // Update는 모디파이로 업데이트한다는걸 알려줘야 한다.
//    @Query(value = " update User u " +
//            " set u.password = :password" +
//            " where u.email = :email ")
//    void updatePassword(@Param("email") String email, @Param("password") String password);
}
