package music.musicsite.repository.reply;

import music.musicsite.entity.board.Board;
import music.musicsite.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByReplyerAndReplyId(String replyer, Long replyID);
    List<Reply> findByBoard(Board board);

    void deleteByBoard_BoardId(Long boardId);
}
