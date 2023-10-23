package music.musicsite.entity.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private int hakbun;
    private String nickname;
    private String password;
    private ROLE role;

    @Builder
    public User(int hakbun, String nickname, String password, ROLE role) {
        this.hakbun = hakbun;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public static User createUser(int hakbun, String nickname, String password) {
        return User.builder()
                .hakbun(hakbun)
                .password(password)
                .nickname(nickname)
                .role(ROLE.ROLE_USER)
                .build();
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
