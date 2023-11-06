package music.musicsite.service.reply;

import music.musicsite.dto.board.ReplyDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.board.Reply;
import music.musicsite.repository.board.BoardRepository;
import music.musicsite.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    public void register(ReplyDTO replyDTO) {
        Board board = boardRepository.findById(replyDTO.getBoardId())
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        replyRepository.save(Reply.from(replyDTO, board));
    }

    // 댓글을 작성한 사용자인지 파악하는 메서드
    public void checkDuplicateNickname(String nickname, Long replyID) {
        replyRepository.findByReplyerAndReplyId(nickname, replyID)
                .orElseThrow(() -> new IllegalArgumentException("작성한 사용자가 아닙니다."));
    }

    // 닉네임이 동일하니 실행
    public void modifyReply(ReplyDTO replyDTO) {
        //JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해 준다. 영속성에 있는 값을찾아 변경
        Reply reply = replyRepository.findByReplyerAndReplyId(replyDTO.getReplyer() ,replyDTO.getReplyId())
                .orElseThrow(() -> new NullPointerException("댓글을 찾을 수 없습니다."));
        reply.changeContent(replyDTO.getContent());
    }

    public void deleteReply(ReplyDTO replyDTO) {
        replyRepository.deleteById(replyDTO.getReplyId());
    }

    public List<ReplyDTO> findAll(Long boardId) {
        List<Reply> replyList = replyRepository.findByBoard(Board.builder().boardId(boardId).build());

        //Reply을 형변환해서 Entity로 바꿔서 넣어줌, 실패시 전역예외처리에서 예외처리함.
        return replyList.stream()
                .map(ReplyDTO::from)
                .collect(Collectors.toList());
    }

    public void deleteAllByBoardId(Long boardId) {//게시글 삭제시 해당 bno 댓글 삭제
            replyRepository.deleteByBoard_BoardId(boardId);
    }
}
