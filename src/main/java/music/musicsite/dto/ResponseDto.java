package music.musicsite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ResponseDto<T> {
    private T data;
    private String msg;

    public ResponseDto(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public ResponseDto(String msg) {
        this.msg = msg;
    }
}
