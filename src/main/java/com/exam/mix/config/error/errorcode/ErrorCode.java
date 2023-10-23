package com.exam.mix.config.error.errorcode;

import org.springframework.http.HttpStatus;



//클라이언트에게 보내줄 에러 코드를 정의해야 한다.
public interface ErrorCode {

    String name();

    HttpStatus getHttpStatus();
    String getMessage();
}
