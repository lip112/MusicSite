package music.musicsite.service.board;

import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.dto.page.PageResultDTO;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.repository.board.BoardRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final EntityManager entityManager;

    public Long Register(BoardDTO boardDTO){
        log.info("boardDTO . . ." + boardDTO);
        Board save = boardRepository.save(Board.from(boardDTO));
        return save.getBoardId();
    }

    @Override
    public List<BoardWithReplyDTO> getBoardId(Long boardId) {
        log.info("get . . ." + boardId);
        return boardRepository.getBoardId(boardId);
    }

    public PageResultDTO<Tuple, BoardDTO> getList(PageRequestDTO pageRequestDTO){
        log.info("getList . . .");

        // 튜플에서 DTO로 변환하는 람다 함수
        Function<Tuple, BoardDTO> fn = (tuple -> BoardDTO.from(tuple.get(0, Board.class), tuple.get(1, String.class), tuple.get(2, Long.class)));

        // 정렬 적용하여 페이지 데이터 가져오기
        Page<Tuple> result = boardRepository.getList(pageRequestDTO.getPageable(Sort.by("boardId").descending()));

        // PageResultDTO 생성 및 반환
        return new PageResultDTO<>(result, fn);
    }

    @Override // 수정 버튼을 누르고 통과 했을때 내용을 수정하고 완료버튼
    public void modify(BoardDTO boardDTO) {
        log.info("modify . . ." + boardDTO);
        Board board = entityManager.find(Board.class, boardDTO.getBoardId());
        String boardWriter = board.getWriter().getNickname();
        //게시판에 작성한 아이디와 비교 ? 1.수정 완료 버튼 클릭시 검사 2차검사
        if (boardWriter.equals(boardDTO.getWriter())) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
        }
    }

    @Override // 수정 버튼눌렀을때 닉네임 체크하고 작성한 사람인지 파악
    public boolean checkDuplicateNickname(String nickname, Long boardId) {
        log.info("checkDuplicateNickname . . ." + nickname + "  boardId =" + boardId);
        Optional<Board> byWriterAndBoardId = boardRepository.findByWriterAndBoardId(nickname, boardId);

        return byWriterAndBoardId.isPresent();
    }

    @Override
    public boolean checkDuplicateNickname(String nickname) {
        log.info("checkDuplicateNickname . . ." + nickname);
        Optional<Board> byWriterAndBoardId = boardRepository.findByWriter(nickname);
        return byWriterAndBoardId.isPresent();
    }

    @Override
    public void delete(Long boardId) {
        log.info("delete . . .");
        try {
            boardRepository.deleteById(boardId);
        } catch (UnexpectedRollbackException e) {
            log.info(e.getMessage(), e);
        }
    }
}
