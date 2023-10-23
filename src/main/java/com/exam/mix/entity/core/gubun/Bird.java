package com.exam.mix.entity.core.gubun;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Builder
@Getter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bird {
    @Id
    private String core_name;
    private int min_lv;
    private int max_lv;
    private String main_core;
    private String sub_core;

}
