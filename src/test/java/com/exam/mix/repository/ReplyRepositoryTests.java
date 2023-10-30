package com.exam.mix.repository;

import music.musicsite.entity.board.Board;
import music.musicsite.entity.board.Reply;
import music.musicsite.repository.reply.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;


    @Test
    public void TestReply() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long) (Math.random() * 100) + 1;
            Board board = Board.builder()
                    .bno(bno)
                    .build();
            Reply reply = Reply.builder()
                    .board(board)
                    .replyer("강낭콩" + i)
                    .content("content..." + i)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    @Transactional
    public void deleteReply() {
        replyRepository.deleteByBoard_Bno(100L);
    }
}
