package com.exam.mix.controller;


import com.exam.mix.dto.core.AllCoreDTO;
import com.exam.mix.service.allcore.AllCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/core")
@Log4j2
public class CoreController {

    private final AllCoreService allCoreService;

    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {
        log.info("list");
        List<String> list = allCoreService.getList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }

    @GetMapping("/search/{core_name}")
    public ResponseEntity<List<AllCoreDTO>> test(@PathVariable("core_name") String core_name) { //url로 받을때만 Param사용
        log.info("core_name = " + core_name);

        System.out.println("all리스트실행 ");
        List<AllCoreDTO> allcoreList = allCoreService.find(core_name);
        System.out.println("Controller coreDto = " + allcoreList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allcoreList);
    }

}