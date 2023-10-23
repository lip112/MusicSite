package com.exam.mix.controller;


import com.exam.mix.dto.board.BoardDTO;
import com.exam.mix.dto.notify.notifyDTO;
import com.exam.mix.dto.page.PageRequestDTO;
import com.exam.mix.dto.page.PageResultDTO;
import com.exam.mix.service.notify.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notify")
public class NotifyController {
    private final NotifyService notifyService;

    @GetMapping("/read/{nno}")
    public ResponseEntity<Object> getread(@PathVariable Long nno) {
        notifyDTO getnno = notifyService.getnno(nno);
        return ResponseEntity.ok()
                .body(getnno);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO> getList(PageRequestDTO pageable) {
        PageResultDTO list = notifyService.getList(pageable);
        return ResponseEntity.ok()
                .body(list);
    }

    @PostMapping("/write")
    public ResponseEntity<Object> BoardWriteForm(@RequestBody notifyDTO notifyDTO){
        notifyService.writeNotify(notifyDTO);
        return ResponseEntity.ok()
                .build(); // 글을 작성하고 성공시 작성된 bno 상세페이지로 이동
    }
}
