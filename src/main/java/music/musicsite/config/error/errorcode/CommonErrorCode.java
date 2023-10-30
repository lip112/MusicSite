package music.musicsite.config.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    //name                key                     message
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    //name을 하면 맨왼쪽에 있는 값이 나옴 얘는 get을 안쓰고 enum.name()만하면 값이 나온다.
    private final HttpStatus httpStatus; // -> get을 사용하면 첫번째 인자가 나옴
    private final String message;// -> getmessage하면 뒤에 두번째 인자가 나옴

}

