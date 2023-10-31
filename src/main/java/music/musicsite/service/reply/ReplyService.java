package music.musicsite.service.reply;


import music.musicsite.dto.board.ReplyDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.board.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> findAll(Long bno);
    ReplyDTO register(ReplyDTO replyDTO);

    void checkDuplicateNickname(String nickname, Long rno);

    void modifyReply(ReplyDTO replyDTO);

    void deleteReply(ReplyDTO replyDTO);

    void deleteAllByBoardId(Long bno);

}
