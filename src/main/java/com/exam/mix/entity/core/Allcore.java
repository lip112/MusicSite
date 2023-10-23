package com.exam.mix.entity.core;



import lombok.*;

import javax.persistence.*;

//db에 테이블이랑같다고 의미
@Builder
@Getter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Allcore  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long core_id;

    private String core_name;
}
