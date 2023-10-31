package music.musicsite.entity.board;

import lombok.*;
import music.musicsite.dto.board.ReplyDTO;
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
    private Long replyId;

    private String content;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) //여러개의 댓글은 하나에 게시물에 속한다.
    private Board board;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeReplyDate;

    public static Reply from(ReplyDTO replyDTO) {
        Board board = Board.builder()
                .boardId(replyDTO.getBoardId())
                .build();
        Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }
    public void changeContent(String content) {
        this.content = content;
    }

}


