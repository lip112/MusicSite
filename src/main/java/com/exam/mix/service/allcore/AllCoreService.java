package com.exam.mix.service.allcore;

import com.exam.mix.dto.core.AllCoreDTO;

import java.util.List;

public interface AllCoreService {

    List<AllCoreDTO> find(String  core_name);

    List<String> getList();


}
