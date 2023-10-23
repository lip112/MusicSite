package com.exam.mix.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO extends BoardWithReplyDTO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private int replyCount;
    private String regDate;
    private String modDate;
}
