package music.musicsite.service.board;

import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.dto.page.PageResultDTO;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.user.User;
import music.musicsite.repository.board.BoardRepository;
import music.musicsite.repository.user.UserRepository;
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
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    //회원가입할때 현재 날짜 구하기
    public Long Register(BoardDTO boardDTO){
        log.info("boardDTO . . ." + boardDTO);
        Optional<User> user = userRepository.findByNickname(boardDTO.getWriter());
        System.out.println("user = " + user);
        Board board = DtoToEntity(boardDTO, user.get());
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public List<BoardWithReplyDTO> getBno(Long bno) {
        log.info("get . . ." + bno);
        return boardRepository.getbno(bno);
    }

    public PageResultDTO<Tuple, BoardDTO> getList(PageRequestDTO pageRequestDTO){
        log.info("getList . . .");

        // 튜플에서 DTO로 변환하는 람다 함수
        Function<Tuple, BoardDTO> fn = (tuple -> EntityToDto(tuple.get(0, Board.class), tuple.get(1, String.class), tuple.get(2, Long.class)));

        // 정렬 적용하여 페이지 데이터 가져오기
        Page<Tuple> result = boardRepository.getList(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        // PageResultDTO 생성 및 반환
        return new PageResultDTO<>(result, fn);
    }

    @Override // 수정 버튼을 누르고 통과 했을때 내용을 수정하고 완료버튼
    public void modify(BoardDTO boardDTO) {
        log.info("modify . . ." + boardDTO);
        Board board = entityManager.find(Board.class, boardDTO.getBno());
        String boardWriter = board.getWriter().getNickname();
        //게시판에 작성한 아이디와 비교 ? 1.수정 완료 버튼 클릭시 검사 2차검사
        if (boardWriter.equals(boardDTO.getWriter())) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
        }
    }

    @Override // 수정 버튼눌렀을때 닉네임 체크하고 작성한 사람인지 파악
    public boolean checkDuplicateNickname(String nickname, Long bno) {
        log.info("checkDuplicateNickname . . ." + nickname + "  BNO =" + bno);
        Board byWriter = boardRepository.findByWriterwithBno(nickname, bno);

        if (byWriter.getBno() == null) {
            return false; // 404 Not Found
        }
        return true; // 200 OK
    }

    @Override
    public void delete(Long bno) {
        log.info("delete . . .");
        try {
            boardRepository.deleteById(bno);
        } catch (UnexpectedRollbackException e) {
            log.info(e.getMessage(), e);
        }
    }
}
