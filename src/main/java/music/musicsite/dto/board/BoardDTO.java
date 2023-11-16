package music.musicsite.dto.board;

import lombok.*;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.user.User;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO extends BoardWithReplyDTO {
    private Long boardId;
    private int category;
    private String title;
    private String content;
    private String writer;
    private int replyCount;
    private String regDate;
    private String modDate;

    public static BoardDTO from(Board board, String nickname, Long replyCount) {
        User writer = board.getWriter();

        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(writer.getNickname())
                .regDate(board.getRegDate().toString())
                .modDate(board.getModDate().toString())
                .category(board.getCategory())
                .build();
        return boardDTO;
    }


}
