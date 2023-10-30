package music.musicsite.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO extends BoardWithReplyDTO{
    public Long rno;
    public String replyer;
    public String content;
    public LocalDateTime writeReplyDate;
    public Long bno;
}
