package music.musicsite.entity.board;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 상속받은 곳에서만 사용가능
@NoArgsConstructor
@ToString(exclude = {"board"})
@EntityListeners(value = { AuditingEntityListener.class})
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String content;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) //여러개의 댓글은 하나에 게시물에 속한다.
    private Board board;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeReplyDate;

    public void changeContent(String content) {
        this.content = content;
    }

}


