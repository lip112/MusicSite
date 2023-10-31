package music.musicsite.dto.response;

import com.querydsl.core.Tuple;
import lombok.Getter;
import lombok.ToString;
import music.musicsite.dto.board.BoardDTO;
import music.musicsite.dto.page.PageResultDTO;

@ToString
@Getter
public class ResponseDto<T> {
    private T data;
    private final String massage;

    public ResponseDto(T data, String massage) {
        this.data = data;
        this.massage = massage;
    }

    public ResponseDto(String massage) {
        this.massage = massage;
    }

    public ResponseDto(PageResultDTO<Tuple, BoardDTO> data, String massage) {
        this.data = (T) data;
        this.massage = massage;
    }

}