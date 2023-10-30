package com.exam.mix.repository;

import music.musicsite.entity.visitor.Visitor;
import music.musicsite.repository.visitor.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class VisitorRepositoryTests {
    @Autowired
    private VisitorRepository visitorRepository;


    @Test
    public void visitorTest() {
        IntStream.rangeClosed(1, 50).forEach(i ->{
                Visitor visitor = Visitor.builder()
                    .email(i + "...@naver.com")
                    .build();

                visitorRepository.save(visitor);
        });

    }

    @Test
    public void totalCountTest() {
        System.out.println(visitorRepository.countBy());
    }

    @Test
    public void incrementVisitorCount() {
        LocalDateTime now = LocalDateTime.now();
        String email = "50...@naver.com";
        
        System.out.println(visitorRepository.findByEmailAndRegDate(email, now).get());
    }

    @Test
    public void TodayCountTest() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(visitorRepository.countByTodayCount(now));
    }
}
