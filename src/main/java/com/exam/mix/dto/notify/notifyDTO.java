package com.exam.mix.dto.notify;

import com.exam.mix.entity.user.Role;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class notifyDTO {
    private Long nno;
    private String title;
    private String content;
}
