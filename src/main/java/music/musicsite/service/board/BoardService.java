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
    List<BoardWithReplyDTO> getBno(Long bno);
    PageResultDTO<Tuple, BoardDTO> getList(PageRequestDTO pageRequestDTO);

    void modify(BoardDTO boardDTO);

    boolean checkDuplicateNickname(String nickname, Long bno);

    void delete(Long bno);

    default Board DtoToEntity(BoardDTO boardDTO) {
        User user = User.builder().nickname(boardDTO.getWriter()).build();
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(user)
                .build();
        return board;
    }

    default Board DtoToEntity(BoardDTO boardDTO, User user) {
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(user)
                .build();
        return board;
    }

    default BoardDTO EntityToDto(Board board, String nickname, Long replyCount) {
        User writer = board.getWriter();

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(writer.getNickname())
                .regDate(board.getRegDate().toString())
                .modDate(board.getModDate().toString())
                .build();
        return boardDTO;
    }


}
