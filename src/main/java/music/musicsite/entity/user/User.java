package music.musicsite.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import music.musicsite.dto.user.UserDTO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@EntityListeners(value = { AuditingEntityListener.class}) // 변경됐을때 알리
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)// auto_increment
    private Long user_id;

    @Column(unique = true, nullable = false) //UK 선언
    private int hakbun;

    private String password;

    @Column(unique = true, nullable = false) //UK 선언
    private String nickname;

    @Enumerated(value = EnumType.STRING) // 또는 @Enumerated(EnumType.STRING) 이고 원래는 INDEX가 저장되지만 0,1,2 name형태 그대로 들어가게 된다.
    private Role role;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Builder
    public User(int hakbun, String nickname, String password, Role role) {
        this.hakbun = hakbun;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public static User from(UserDTO userDTO) {
        return User.builder()
                .hakbun(userDTO.getHakbun())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .role(userDTO.getRole())
                .build();
    }


    public void changePassword(String password) {
        this.password = password;
    }
}
