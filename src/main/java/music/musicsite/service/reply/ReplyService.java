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

    void deleteAllByBno(Long bno);

    default Reply DtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder()
                .bno(replyDTO.getBno())
                .build();
        Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }

    default ReplyDTO EntityToDto(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(reply.getBoard().getBno())
                .rno(reply.getRno())
                .writeReplyDate(reply.getWriteReplyDate())
                .content(reply.getContent())
                .replyer(reply.getReplyer())
                .build();
        return replyDTO;
    }
}
