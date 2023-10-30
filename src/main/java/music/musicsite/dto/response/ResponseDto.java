package music.musicsite.dto.response;

import lombok.Getter;
import lombok.ToString;

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
}