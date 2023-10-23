package com.exam.mix.dto.visitor;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class VisitorDTO {
    int TotalVisitorCount;
    int TodayVisitorCount;
    String email;
    LocalDateTime regDate;
}
