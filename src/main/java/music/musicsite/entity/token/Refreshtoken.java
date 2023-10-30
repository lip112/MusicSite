package music.musicsite.entity.token;


import music.musicsite.entity.user.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Refreshtoken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String refreshToken;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
