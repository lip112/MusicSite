package com.exam.mix.dto.core;




//클라이언트에서 이름받아올떄사용

import lombok.*;

@ToString
@Data
@Builder
public class CoreDTO {
    private String core_name;
    private int core_id;


}


