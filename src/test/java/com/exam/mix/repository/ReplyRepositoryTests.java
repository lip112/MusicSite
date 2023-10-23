package com.exam.mix.repository;

import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.board.Reply;
import com.exam.mix.repository.reply.ReplyRepository;
import com.exam.mix.service.reply.ReplyService;
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
