package music.musicsite.controller;

import music.musicsite.dto.board.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.service.reply.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/write")
    public ResponseEntity<ResponseDto<String>> registerReply(@RequestBody final ReplyDTO replyDTO) {
        log.info("registerReply" + replyDTO);
        replyService.register(replyDTO);
        return ResponseEntity.ok(new ResponseDto<>("댓글을 작성했습니다."));
    }

    //댓글 삭제, 수정할때 똑같이 gno와 nickname을 체크해서 반환하기
    @GetMapping({"/modify/{replyId}/{nickname}", "/delete/{replyId}/{nickname}"}) // 수정 버튼 누를때 작성한 사용자인지 닉네임 체크
    public ResponseEntity<ResponseDto<String>> modifyReplyCheckNickname(@PathVariable("replyId") Long rno, @PathVariable("nickname") String nickname) {
        log.info("modifyReplyCheckNickname" + rno + nickname);
        replyService.checkDuplicateNickname(nickname, rno);
        return ResponseEntity.ok(new ResponseDto<>("작성된 사용자입니다."));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseDto<String>> modifyReply(@RequestBody ReplyDTO replyDTO) {
        log.info("modifyReply" + replyDTO);
        replyService.modifyReply(replyDTO);
        return ResponseEntity.ok(new ResponseDto<>("댓글을 수정했습니다."));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<String>> deleteReply(@RequestBody ReplyDTO replyDTO) {
        log.info("deleteReply" + replyDTO);
        replyService.deleteReply(replyDTO);
        return ResponseEntity.ok(new ResponseDto<>("삭제가 완료 되었습니다."));
    }

}
