package music.musicsite.controller;

import music.musicsite.dto.board.ReplyDTO;
import music.musicsite.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/reply")
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerReply(@RequestBody final ReplyDTO replyDTO) {
        log.info("registerReply" + replyDTO);
        replyService.register(replyDTO);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/modify")
    public ResponseEntity<Void> modifyReply(@RequestBody ReplyDTO replyDTO) {
        log.info("modifyReply" + replyDTO);
        replyService.modifyReply(replyDTO);
        return ResponseEntity.ok()
                .build();
    }

    //댓글 삭제, 수정할때 똑같이 gno와 nickname을 체크해서 반환하기
    @GetMapping({"/modify/{rno}/{nickname}", "/delete/{rno}/{nickname}"}) // 수정 버튼 누를때 작성한 사용자인지 닉네임 체크
    public ResponseEntity<Void> modifyReplyCheckNickname(@PathVariable("rno") Long rno, @PathVariable("nickname") String nickname) {
        log.info("modifyReplyCheckNickname" + rno + nickname);
        replyService.checkDuplicateNickname(nickname, rno);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReply(@RequestBody ReplyDTO replyDTO) {
        log.info("deleteReply" + replyDTO);
        replyService.deleteReply(replyDTO);
        return ResponseEntity.ok()
                .build();
    }

}
