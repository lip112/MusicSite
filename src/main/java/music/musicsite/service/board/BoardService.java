package music.musicsite.service.board;

import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.dto.page.PageResultDTO;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.user.User;
import com.querydsl.core.Tuple;

import java.util.List;

public interface BoardService {

    Long Register(BoardDTO boardDTO);
    List<BoardWithReplyDTO> getBoardId(Long bno);
    PageResultDTO<Tuple, BoardDTO> getList(PageRequestDTO pageRequestDTO, BoardDTO boardDTO);

    void modify(BoardDTO boardDTO);

    boolean checkDuplicateNickname(String nickname, Long bno);
    boolean checkDuplicateNickname(String nickname);

    void delete(Long bno);

}
