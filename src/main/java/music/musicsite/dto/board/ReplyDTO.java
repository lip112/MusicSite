package music.musicsite.dto.board;

import lombok.*;
import music.musicsite.entity.board.Reply;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO extends BoardWithReplyDTO{
    public Long replyId;
    public String replyer;
    public String content;
    public LocalDateTime writeReplyDate;
    public Long boardId;


    public static ReplyDTO from(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .boardId(reply.getBoard().getBoardId())
                .replyId(reply.getReplyId())
                .writeReplyDate(reply.getWriteReplyDate())
                .content(reply.getContent())
                .replyer(reply.getReplyer())
                .build();
        return replyDTO;
    }
}
