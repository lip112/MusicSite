package music.musicsite.entity.visitor;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)//@CreatedDate를 설정했을떄 변경되는지 판단한다.
public class Visitor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vid;

    @CreatedDate
    private LocalDateTime regDate;

    private String email;

}
