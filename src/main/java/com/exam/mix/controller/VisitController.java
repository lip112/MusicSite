package com.exam.mix.controller;

import com.exam.mix.service.visitor.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/visit")
public class VisitController {
    private final VisitorService visitorService;

    @GetMapping("/count")
    public Map<String, Integer> TodayVisitor() {
        Map<String, Integer> map = new HashMap<>();
        map.put("today",visitorService.getTodayVisitorCount());
        map.put("total",visitorService.getTotalVisitorCount());
        return map;
    }
}
