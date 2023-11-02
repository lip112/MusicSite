package music.musicsite.controller;

import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import music.musicsite.service.reply.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/list")//board/list?page=1 같은 값이들어오면 자동으로 매핑해준다.
    public ResponseEntity<ResponseDto<String>> ListForm(PageRequestDTO pageRequestDTO) {
        log.info("ListForm" + pageRequestDTO);
        return ResponseEntity.ok(new ResponseDto<>(boardService.getList(pageRequestDTO), "리스트를 성공적으로 불러왔습니다."));
    }

    @GetMapping("/read/{boardId}") // board/read/2
    public ResponseEntity<ResponseDto<List<BoardWithReplyDTO>>> ViewBoard(@PathVariable("boardId") Long boardId) {
        log.info("BoradboardIdForm" + boardId);

        return ResponseEntity.ok(new ResponseDto<>(boardService.getBoardId(boardId), "게시글 상세페이지를 불러왔습니다."));
    }

    @PostMapping("/write")
    public ResponseEntity<ResponseDto<Long>> BoardWriteForm(@RequestBody BoardDTO boardDTO) {
        log.info("BoardWrite" + boardDTO);

        Long boardId = boardService.Register(boardDTO);
        return ResponseEntity.ok(new ResponseDto<>(boardId, "게시글 작성이 완료되었습니다."));
    }

    //게시글 수정 및 삭제시 버튼 클릭시 닉네임 체크
    @GetMapping({"/modify/{boardId}/{nickname}", "/delete/{boardId}/{nickname}"})
    public ResponseEntity<ResponseDto<String>> modifyAndDelteNicknameCheck(@PathVariable("nickname") String nickname, @PathVariable("boardId") Long boardId) {
        log.info("modifyAndDelteNicknameCheck" + nickname);

        if (boardService.checkDuplicateNickname(nickname, boardId)) {
            return ResponseEntity.ok(new ResponseDto<>("작성한 사용자가 맞습니다."));
        } else {
            return ResponseEntity.ok(new ResponseDto<>("작성한 사용자가 아닙니다."));
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseDto<Long>> modifyBoard(@RequestBody BoardDTO boardDTO) {
        log.info("modifyBoard" + boardDTO);
        boardService.modify(boardDTO);
        return ResponseEntity.ok(new ResponseDto<>(boardDTO.getBoardId(), "게시글 수정이 완료되었습니다."));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<String>> deleteBoard(@RequestBody BoardDTO boardDTO) {
        log.info("deleteBoard...." + boardDTO);
        replyService.deleteAllByBoardId(boardDTO.getBoardId());
        boardService.delete(boardDTO.getBoardId());
        return ResponseEntity.ok(new ResponseDto<>("게시글 삭제가 완료되었습니다."));
    }
}
