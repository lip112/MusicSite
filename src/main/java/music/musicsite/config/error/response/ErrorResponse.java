package music.musicsite.config.error.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
/*아래와 같음 에러응답을 보내주려고 한다.
        {
        "code": "INACTIVATE_USER",
        "message": "User is inactive"
        }
*/
public class ErrorResponse {
    private final String code;
    private final String message;

    //속성 값이 null이거나 비어있는 경우 해당 속성을 JSON 결과에서 제외합니다. 즉, 속성 값이 존재하는 경우에만 해당 속성이 JSON에 포함되도록 지정하는 것입니다.
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}