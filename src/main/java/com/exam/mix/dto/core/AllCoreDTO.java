package com.exam.mix.dto.core;


import lombok.*;

//객체배열로 담을때 다향성이용해서 사용해야해서 전체로 하나만듬
@ToString
@Data
@Builder
public class AllCoreDTO {
    private Long core_id;
    private String core_name;
    private int min_lv;
    private int max_lv;
    private String main_core;
    private String sub_core;


}
