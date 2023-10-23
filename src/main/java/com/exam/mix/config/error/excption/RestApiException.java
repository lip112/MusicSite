package com.exam.mix.config.error.excption;

import com.exam.mix.config.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    //언체크예외를 받았다. 체크예외는 어차피 받아도 불필요하게 throw를 던져야하기 때문이다.
    private final ErrorCode errorCode;
}
