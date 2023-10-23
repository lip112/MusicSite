package music.musicsite.repository.user;

import music.musicsite.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByHakbunAndPassword(int hakbun, String password);

    Optional<User> findByHakbun(int hakbun);
}
