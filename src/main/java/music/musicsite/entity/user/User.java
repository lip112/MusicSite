package music.musicsite.entity.user;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@EntityListeners(value = { AuditingEntityListener.class}) // 변경됐을때 알리
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)// auto_increment
    private Long uid;

    @Column(unique = true, nullable = false) //UK 선언
    private String email;

    private String password;

    @Column(unique = true, nullable = false) //UK 선언
    private String nickname;

    @Enumerated(value = EnumType.STRING) // 또는 @Enumerated(EnumType.STRING) 이고 원래는 INDEX가 저장되지만 0,1,2 name형태 그대로 들어가게 된다.
    private Role role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

}
