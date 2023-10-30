package music.musicsite.controller;

import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.dto.page.PageResultDTO;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.service.reply.ReplyService;
import music.musicsite.service.board.BoardService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/list")//board/list?page=1 같은 값이들어오면 자동으로 매핑해준다.
    public PageResultDTO<Tuple, BoardDTO> ListForm(PageRequestDTO pageRequestDTO) {
        log.info("ListForm" + pageRequestDTO);
       return boardService.getList(pageRequestDTO);
    }

    @GetMapping("/read/{bno}") // board/read/2
    public List<BoardWithReplyDTO> BoradBnoForm(@PathVariable("bno") Long bno){
        log.info("BoradBnoForm" + bno);

        return boardService.getBno(bno);
    }

    @PostMapping("/write")
    public Long BoardWriteForm(@RequestBody BoardDTO boardDTO){
        log.info("BoardWriteForm" + boardDTO);

        Long bno = boardService.Register(boardDTO);
        return bno; // 글을 작성하고 성공시 작성된 bno 상세페이지로 이동
    }

    //게시글 수정 및 삭제시 버튼 클릭시 닉네임 체크
    @GetMapping({"/modify/{bno}/{nickname}", "/delete/{bno}/{nickname}"})
    public ResponseEntity<String> modifyAndDelteNicknameCheck(@PathVariable("nickname") String nickname, @PathVariable("bno") Long bno){
        log.info("modifyAndDelteNicknameCheck" + nickname);
        boardService.checkDuplicateNickname(nickname, bno);
        return ResponseEntity.ok()
                .body(nickname);
    }

    @PutMapping("/modify")
    public ResponseEntity<Long> modifyBoard(@RequestBody BoardDTO boardDTO) {
        log.info("modifyBoard" + boardDTO);
        boardService.modify(boardDTO);
        return ResponseEntity.ok()
                .body(boardDTO.getBno());
    }

    @DeleteMapping("/delete")
    public String deleteBoard(@RequestBody BoardDTO boardDTO) { //Json으로 key값을 동일하게 받는다.
        log.info("deleteBoard...." + boardDTO);
        replyService.deleteAllByBno(boardDTO.getBno());
        boardService.delete(boardDTO.getBno());
        return "redirect:/board/list";
    }
}
