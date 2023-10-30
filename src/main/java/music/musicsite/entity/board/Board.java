package music.musicsite.entity.board;

import music.musicsite.dto.board.BoardDTO;
import music.musicsite.entity.TimeTable;
import music.musicsite.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자(NoArgsConstructor)의 접근 제어를 PROCTECTED 로 설정하면 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막게 됩니다. 즉 무분별한 객체 생성에 대해 한번 더 체크할 수 있습니다. builder로 생성할때는 가능
@NoArgsConstructor
@ToString(exclude = "writer")
public class Board extends TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    // 여러개의 게시물은 하나의 작성자를 보유한다., fetch = FetchType.LAZY는 필요할 때만 조회 하게 만들기
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User writer;


    //Entity레벨에서는 setter를 쓰지 않는게 좋아서 chage로 작성
    public void changeTitle(String title) {
        this.title = title;
    }


    public void changeContent(String content) {
        this.content = content;
    }

    public Board DtoToEntity(BoardDTO boardDTO) {
        User user = User.builder().nickname(boardDTO.getWriter()).build();
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(user)
                .build();
        return board;
    }

    public BoardDTO EntityToDto(Board board, String nickname, Long replyCount) {
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
