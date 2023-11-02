package music.musicsite.controller;

import music.musicsite.service.visitor.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visit")
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
